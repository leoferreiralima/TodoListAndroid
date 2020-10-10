package br.com.creative.todolist;

public enum TodoResult {
    CREATED(1000),EDITED(1001),CANCELED(1002);

    private final int result;
    TodoResult(int result) {
        this.result = result;
    }

    public int getResult() {
        return result;
    }

    public static TodoResult fromResult(int result){
        for( TodoResult todoResult : TodoResult.values())
            if(result == todoResult.getResult()) return todoResult;
        return null;
    }
}
