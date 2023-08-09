package me.catto.AutoOBS.tasks.data;

public class TriggersSchema {
    private final Server[] servers;

    public TriggersSchema(Server[] servers) {
        this.servers = servers;
    }

    public Server[] getServers() {
        return servers;
    }
}
