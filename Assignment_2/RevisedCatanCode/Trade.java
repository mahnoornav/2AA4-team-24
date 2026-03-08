public interface Trade {
    
    // store info about the current trade
    public void proposeTrade(Player requestsTrade, Player recievesTrade, Map<ResourceType, Integer> resourceRequested, Map<ResourceType, Integer> resourceOffered) {
        
    }

    private void tradeBank(Player requestsTrade, ResourceType offer, ResourceType recieve) {
    }

    private void accept(){
    }

    private void reject() {
    }
}