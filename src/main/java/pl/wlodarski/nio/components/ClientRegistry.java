package pl.wlodarski.nio.components;

import io.netty.channel.ChannelId;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import pl.wlodarski.nio.EyetrackerMsg;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Data
public class ClientRegistry {
    private static ClientRegistry clientRegistry = new ClientRegistry();
    Map<String, EyetrackerMsg> eyetrackerMsgMap = new ConcurrentHashMap<>();
    Map<ChannelId, String> channelIdStringMap = new ConcurrentHashMap<>();

    private ClientRegistry() {
    }

    public static ClientRegistry getInstance() {
        return clientRegistry;
    }


    public Map<String, EyetrackerMsg> getClientsData() {
        return getEyetrackerMsgMap();
    }

    public void deleteClientData(ChannelId channelId) {
        String associatedId = channelIdStringMap.get(channelId);
        log.info("Removed client : " + associatedId + " data");
        eyetrackerMsgMap.remove(associatedId);
        channelIdStringMap.remove(channelId);
    }

    public synchronized Map<String, EyetrackerMsg> getAndUpdateData(EyetrackerMsg msg, ChannelId channelId) {
        updateChannelIdMapIfNeeded(msg, channelId);
        eyetrackerMsgMap.put(msg.getId(), msg);
        return eyetrackerMsgMap;
    }

    private void updateChannelIdMapIfNeeded(EyetrackerMsg msg, ChannelId channelId) {
        if (!channelIdStringMap.containsKey(channelId)) {
            channelIdStringMap.put(channelId, msg.getId());
        }
    }


}
