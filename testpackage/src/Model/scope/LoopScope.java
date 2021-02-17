package Model.scope;

class LoopScope extends Scope{
    LoopScope(Scope parentScope) {
        super(parentScope);
    }

    @Override
    boolean isLoop() {
        return true;
    }
}
