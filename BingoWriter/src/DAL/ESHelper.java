package DAL;

import BLL.Common.LogManager;
import UI.BingoWriterUI;

import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.net.InetAddress;
import java.util.List;

public class ESHelper {
    private LogManager logManager = new LogManager();

    private TransportClient GetTransClient(String esCluster, String host, int port) {
        Settings settings = Settings.builder()
                .put("cluster.name", esCluster)
                .put("client.transport.sniff", true)
                .build();
        TransportClient transClient = null;
        try {
            transClient = new PreBuiltTransportClient(settings);
            transClient.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(host), port));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return transClient;
    }

    public boolean ConnectToES(String esCluster, String host, int port) {
        boolean ret = true;
        if (GetTransClient(esCluster, host, port) == null) {
            ret = false;
        }
        return ret;
    }

    public int UpsertData(BingoWriterUI ui, List<String> jsonList, String indexName, String typeName, int indexNum) {
        TransportClient transClient = null;
        int errorCount = 0;
        try {
            transClient = GetTransClient(
                    ui.modelConfigInfo.getESCluster(),
                    ui.modelConfigInfo.getESHost(),
                    ui.modelConfigInfo.getESPort()
            );
            String firstJsonStr = jsonList.get(0);
            String firstId = firstJsonStr.substring(firstJsonStr.indexOf("\"id\":\"") + 6, firstJsonStr.lastIndexOf("\"}"));
            int idHash = Math.abs(firstId.hashCode());
            int indexTail = idHash % indexNum;
            indexName += ("_" + String.valueOf(indexTail));
            BulkRequestBuilder bulkRequest = transClient.prepareBulk();
            for (String json : jsonList) {
                try {
                    String id = json.substring(json.indexOf("\"id\":\"") + 6, json.lastIndexOf("\"}"));
                    String newJson = json.substring(0, json.indexOf("\"id\":\"") - 1) + "}";
                    IndexRequest indexRequest = new IndexRequest(indexName, typeName, id);
                    indexRequest.source(newJson);
                    UpdateRequest updateRequest = new UpdateRequest(indexName,typeName, id);
                    updateRequest.doc(newJson).upsert(indexRequest);
                    bulkRequest.add(transClient.prepareIndex(indexName, typeName, id).setSource(newJson));
                } catch (Exception e) {
                    logManager.LogRecord(
                            ui.modelConfigInfo.getLogFilePath(),
                            ui.modelConfigInfo.getInputFileName(),
                            "[upsert error] " + e.getMessage() + "\n[JSON] " + json,
                            "ES"
                    );
                    errorCount++;
                }
            }
            bulkRequest.execute().actionGet();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (transClient != null) {
                    transClient.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return errorCount;
    }
}
