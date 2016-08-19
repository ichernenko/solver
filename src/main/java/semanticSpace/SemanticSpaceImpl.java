package semanticSpace;

class SemanticSpaceImpl implements SemanticSpace {
    private static SemanticSpace space = new SemanticSpaceImpl();

    private SemanticSpaceImpl() {
    }

    public static SemanticSpace getInstance() {
        return space;
    }

    @Override
    public void putNode(SemanticNode semanticNode) {

    }

    @Override
    public String getAnswer(Question question) {
        return null;
    }
}
