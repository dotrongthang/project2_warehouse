package com.example.project2_warehouse.Interfaces;

import com.example.project2_warehouse.Model.GoodsIssue;
import com.example.project2_warehouse.Model.GoodsReceipt;

public interface IChangeIP {
    void DeleteGoodsReceipt(GoodsReceipt goodsReceipt);
    void EditGoodsReceipt(GoodsReceipt goodsReceipt);
    void Success(int key);

    void DeleteGoodsIssue(GoodsIssue goodsIssue);

    void EditGoodsIssue(GoodsIssue goodsIssue);
}
