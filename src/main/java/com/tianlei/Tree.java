package com.tianlei;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tianlei on 2017/10/9.
 */
public class Tree {

    //点位
    private class DianWei {

    public Long id;
    public Long parentId; //父id
    public String userId;
    public Integer xiaoLiBaoCount; //已获得小礼包数量

    }


//    获取当前树有多少个节点
    private Integer getCurrentMaxNodeCount() {

        // select count from t_treee;
        return 1;
    }


    //    构造树，购买点位
    //    插入节点
    public void insertNode(String userId) {
        Integer currentNodeCount = this.getCurrentMaxNodeCount();

        Long id = new Long(0);
        Long parentId = new Long(0);

        if (currentNodeCount == 0) {
            //
         id = new Long(1);
         parentId = new Long(0);

        } else  {

            id = currentNodeCount + new Long(1);
            parentId =  id % 2 == 0 ?  id/2 :  (id - 1)/2;

        }

        if (id != new Long(0)) {

            DianWei dianWei =  new DianWei();
            dianWei.id = id;
            dianWei.parentId = parentId;
            dianWei.userId = userId;

            //begin
            this.insertNodeToDB(dianWei);

            //获得第一次保底
            this.getFirstDaoDi(dianWei);

            //大礼包
            this.sendDaLiBao(dianWei);

            //小礼包
            this.sendXiaoLiBao(dianWei);

            //commit

        }

    }

    private  void insertNodeToDB(DianWei dianWei) {

//        insert into t_tree() values()

    }

//  购买点位时获得第一次保底
    private void getFirstDaoDi(DianWei dianWei) {

        String userId = dianWei.userId;
        //根据userId 添加相应金额

    }

//    分发大礼包
    private void sendDaLiBao(DianWei dianWei) {

        Long dianWeiId = dianWei.id;
        if (dianWeiId <= 26) {

            for (Long i = new Long(1); i <= dianWeiId; i ++) {

                //根据 id 分发大礼包

            }

        } else  {

            Long i = dianWeiId - 26;
            for (; i < dianWeiId; i ++) {

                //根据 id 分发大礼包
            }

        }

    }

//  分发小礼包
    private void sendXiaoLiBao(DianWei dianWei) {

        Long dianWeiId = dianWei.id;

        //判断父点位
        if (dianWeiId == 1) {
            return;
        }

//      先找出 网体内可以获得 小礼包的 id
        List<Long> idList = new ArrayList();

        if (dianWeiId % 2 == 0) {
            //偶
            //找出 可获得小礼包的Id

        } else  {
            //奇数
            //找出 可获得小礼包的Id

        }

        //应该由上向下搜索
        for (Integer i = idList.size() - 1; i >= 0; i --) {

            DianWei currentDianWei = this.getDianWeiById(idList.get(i.intValue()));

            //向上搜索
            if (dianWei.xiaoLiBaoCount < 40) {

                dianWei.xiaoLiBaoCount ++;

            } else  {
                //根据规则，向下搜索
                Long  nextDianWeiId = currentDianWei.parentId;

                //
                while(nextDianWeiId < dianWeiId){

                    DianWei nextDianWei  = this.getDianWeiById(nextDianWeiId);
                    if (nextDianWei.xiaoLiBaoCount < 40) {

                        nextDianWei.xiaoLiBaoCount ++ ;
                        break;

                    } else {

                        nextDianWeiId = nextDianWei.parentId;
                        continue;

                    }
                }
                //

            }

        }

    }

    private DianWei getDianWeiById(Long id) {

        return new DianWei();

    }

}
