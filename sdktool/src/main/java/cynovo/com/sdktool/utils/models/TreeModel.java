package cynovo.com.sdktool.utils.models;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

/**
 * Created by cynovo on 2016-06-13.
 * 以目录结构的形式来表示多级菜单之间的关系，根目录是root
 */
public class TreeModel {
    private Map<String, Object> mRootMap;
    private Stack<Map<String, Object>> mIndexMapStack;
    private Map<String, Object> mIndexMap;
    public TreeModel(){
        mRootMap = new HashMap<String, Object>();
        mIndexMapStack = new Stack<Map<String, Object>>();
        mIndexMap = mRootMap;
    }

    public boolean entry(String subTree){
        if(mIndexMap.containsKey(subTree)){
            Object tmp = mIndexMap.get(subTree);
            if(tmp instanceof Map) {
                mIndexMapStack.push(mIndexMap);
                mIndexMap = (Map<String, Object>)tmp;
                return true;
            }
        }
        return false;
    }

    public boolean exit(){
        if(mIndexMapStack.isEmpty()){
            return false;
        }
        mIndexMap = mIndexMapStack.pop();
        return true;
    }

    public void returnRoot(){
        mIndexMap = mRootMap;
        mIndexMapStack.clear();
    }

    public void addTree(String subTree){
        if(mIndexMap.containsKey(subTree)){
        }else{
            mIndexMap.put(subTree, new HashMap<String, Object>());
        }
    }

    public void addItem(String itemName, Object item){
        mIndexMap.put(itemName, item);
    }

    public List<String> getTree(){
        List<String> list = new ArrayList<String>();

        Set<Map.Entry<String, Object>> entries = mIndexMap.entrySet();
        for (Map.Entry<String, Object> item:entries) {
            if(item.getValue() instanceof Map){
                list.add(item.getKey());
            }
        }
        return list;
    }

    public List<String> getItem(){
        List<String> list = new ArrayList<String>();

        Set<Map.Entry<String, Object>> entries = mIndexMap.entrySet();
        for (Map.Entry<String, Object> item:entries) {
            if(! (item.getValue() instanceof Map)){
                list.add(item.getKey());
            }
        }
        return list;
    }

    //目录结构，先查找根目录，一层一层遍历
    static public void print(TreeModel treeModel, String subTree){
        if(!subTree.equals("root"))
            if(!treeModel.entry(subTree))
                return;
        List<String> treeList = treeModel.getTree();
        List<String> itemList = treeModel.getItem();
        Log.d("xxx", String.format("%s %s %s", subTree, treeList.toString(), itemList.toString()));
        for(String tree: treeList){
            print(treeModel, tree);
        }
        treeModel.exit();
    }

}
