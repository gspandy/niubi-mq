package com.niubimq.dao.impl;


import java.util.List;

import com.niubimq.dao.BaseDao;
import com.niubimq.dao.MsgPushServiceDao;
import com.niubimq.pojo.Message;

/**
 * 反向消费服务DAO
 * @author junjin4838
 * */
public class MsgPushServiceDaoImpl extends BaseDao implements MsgPushServiceDao {

    public void insertConsumedMsg(Message msg) {
        sqlSession.insert("insertConsumedMsg", msg);
    }
    
    public void insertConsumedMsgBatch(List<Message> msgList) {
        if(msgList.size()==0)return;
        sqlSession.insert("insertConsumedMsgBatch", msgList);
    }

    public void insertTimmingMsg(Message msg) {
        sqlSession.insert("insertTimmingMsg", msg);
    }
    
    public void insertTimmingMsgBatch(List<Message> msgList) {
        if(msgList.size()==0)return;
        sqlSession.insert("insertTimmingMsgBatch", msgList);
    }

    public List<Message> selectTimmingMsg(String dataLock) {
        return sqlSession.selectList("selectTimmingMsg", dataLock);
    }

    public void lockTimmingMsg(String dataLock) {
        sqlSession.update("lockTimmingMsg", dataLock);
    }

    public void delTimmingMsg(String dataLock) {
        sqlSession.delete("delTimmingMsg", dataLock);
    }
}
