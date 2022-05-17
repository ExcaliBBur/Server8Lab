package Models;

import Interaction.Sender;
import Interfaces.Informable;

import java.net.SocketAddress;
import java.nio.channels.DatagramChannel;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Informer<T> implements Informable<T> {
    private DatagramChannel channel;
    private Set<SocketAddress> connectedUsers;

    public Informer(DatagramChannel channel, Set<SocketAddress> connectedUsers) {
        this.channel = channel;
        this.connectedUsers = connectedUsers;
    }

    @Override
    public void inform(Collection<T> collection) {
        try {
            List<Callable<Object>> list = new ArrayList<>();

            this.getConnectedUsers().forEach(x -> list.add(Executors.callable(new Sender<>(channel,
                    new ServerDTO<>(collection, ServerDTO.DTOType.UPDATE), x))));

            ExecutorService executor = Executors.newCachedThreadPool();
            executor.invokeAll(list);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public Set<SocketAddress> getConnectedUsers() {
        return connectedUsers;
    }
}
