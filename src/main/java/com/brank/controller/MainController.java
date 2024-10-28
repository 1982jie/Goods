package com.brank.controller;


import com.brank.dao.bo.GoodsBo;
import com.brank.dao.bo.PurchasedGoodsBo;
import com.brank.util.Commodity;
import org.fisco.bcos.sdk.abi.datatypes.generated.tuples.generated.Tuple2;
import org.fisco.bcos.sdk.abi.datatypes.generated.tuples.generated.Tuple3;
import org.fisco.bcos.sdk.abi.datatypes.generated.tuples.generated.Tuple4;
import org.fisco.bcos.sdk.model.TransactionReceipt;
import org.fisco.bcos.sdk.transaction.model.exception.ContractException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class MainController {

    @Autowired
    private Commodity commodity;

    @GetMapping("/index")
    public String index() {
        return "Welcome to Brank!";
    }

    @PostMapping("/addRole")
    public String addRole(@RequestParam BigInteger id,@RequestParam String name,@RequestParam BigInteger role) {
        TransactionReceipt tr = commodity.addRole(id, name, role);
        if (tr.isStatusOK()) {
            return "success!";
        } else {
            return tr.getMessage();
        }
    }

    @PostMapping("/getRole")
    public Map<String,Object> getRole(@RequestParam BigInteger id) throws ContractException {
        Map<String,Object> map = new TreeMap<>();
        List list = listId();
        if (list.contains(id)) {
            Tuple2<String, BigInteger> role = commodity.getRole(id);
            map.put("name",role.getValue1());
            map.put("role",role.getValue2());
            return map;
        } else {
            map.put("error","id not exist!");
            return map;
        }
    }

    @GetMapping("listId")
    public List listId() throws ContractException {
        return commodity.listId();
    }

    @PostMapping("/raiseRights")
    public String raiseRights(@RequestParam BigInteger id,@RequestParam BigInteger role) {
        TransactionReceipt tr = commodity.raiseRights(id, role);
        if (tr.isStatusOK()) {
            return "success!";
        } else {
            return tr.getMessage();
        }
    }

    @PostMapping("/addGoods")
    public String addGoods(@RequestBody GoodsBo goodsBo) {
        TransactionReceipt tr = commodity.addGoods(goodsBo.getRoleId(), goodsBo.getId(), goodsBo.getName(), goodsBo.getPurchasePrice(), goodsBo.getSellingPrice(), goodsBo.getStock());
        if (tr.isStatusOK()) {
            return "success!";
        } else {
            return tr.getMessage();
        }
    }

    @PostMapping("/getGoods")
    public Map<String,Object> getGoods(@RequestParam BigInteger id) throws ContractException {
        Map<String,Object> map = new TreeMap<>();
        List list = listGoods();
        if (list.contains(id)) {
            Tuple4<String, BigInteger, BigInteger, List<BigInteger>> goods = commodity.getGoods(id);
            map.put("name",goods.getValue1());
            map.put("sellingPrice",goods.getValue2());
            map.put("stock",goods.getValue3());
            map.put("evaluate",goods.getValue4());
            return map;
        } else {
            map.put("error","id not exist!");
            return map;
        }
    }

    @GetMapping("listGoods")
    public List listGoods() throws ContractException {
        return commodity.listGoods();
    }

    @GetMapping("/purchased")
    public String purchased(@RequestBody PurchasedGoodsBo purchasedGoodsBo) {
        TransactionReceipt tr = commodity.purchased(purchasedGoodsBo.getId(), purchasedGoodsBo.getNum(), purchasedGoodsBo.getPrice(), purchasedGoodsBo.getEvaluation());
        if (tr.isStatusOK()) {
            return "success!";
        } else {
            return tr.getMessage();
        }
    }

    @GetMapping("/getGoodsEvaluate")
    public Map<String,Object> getGoodsEvaluate(@RequestParam BigInteger id) throws ContractException {
        Map<String,Object> map = new TreeMap<>();
        List list = listGoods();
        if (list.contains(id)) {
            Tuple3<BigInteger, BigInteger, BigInteger> goodsEvaluate = commodity.getGoodsEvaluate(id);
            map.put("good",goodsEvaluate.getValue1());
            map.put("general",goodsEvaluate.getValue2());
            map.put("poor",goodsEvaluate.getValue3());
            return map;
        } else {
            map.put("error","id not exist!");
            return map;
        }
    }

    @PostMapping("/addStock")
    public String addStock(@RequestParam BigInteger roleId,@RequestParam BigInteger id,@RequestParam BigInteger stock) {
        TransactionReceipt tr = commodity.addStock(roleId,id,stock);
        if (tr.isStatusOK()) {
            return "success!";
        } else {
            return tr.getMessage();
        }
    }

    @PostMapping("/changePrice")
    public String changePrice(@RequestParam BigInteger roleId,@RequestParam BigInteger id,@RequestParam BigInteger price) {
        TransactionReceipt tr = commodity.reducePrice(roleId,id,price);
        if (tr.isStatusOK()) {
            return "success!";
        } else {
            return tr.getMessage();
        }
    }

    @GetMapping("getFunds")
    public BigInteger getFunds() throws ContractException {
        return commodity.getFunds();
    }
}
