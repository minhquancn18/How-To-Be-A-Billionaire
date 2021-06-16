package com.example.myproject22.Model;

import java.util.Date;

public class DetailItem {
    private Double _MONEY;
    private String _DESCRIPTION;
    private String _DATE;
    private String _NAME;
    private int _TYPE;
    private String _IMAGE;
    private String _IMAGECATEGORY;
    private String _AUDIO;

    public Date get_DATEFULL() {
        return _DATEFULL;
    }

    public void set_DATEFULL(Date _DATEFULL) {
        this._DATEFULL = _DATEFULL;
    }

    private Date _DATEFULL;

    public DetailItem(Double _MONEY, String _DESCRIPTION, String _DATE, String _NAME, int _TYPE, String _IMAGE, String _AUDIO) {
        this._MONEY = _MONEY;
        this._DESCRIPTION = _DESCRIPTION;
        this._DATE = _DATE;
        this._NAME = _NAME;
        this._TYPE = _TYPE;
        this._IMAGE = _IMAGE;
        this._AUDIO = _AUDIO;
    }

    public DetailItem(Double _MONEY, String _DESCRIPTION, String _DATE, String _NAME, int _TYPE, String _IMAGE, String _IMAGECATEGORY, String _AUDIO, Date _DATEFULL) {
        this._MONEY = _MONEY;
        this._DESCRIPTION = _DESCRIPTION;
        this._DATE = _DATE;
        this._NAME = _NAME;
        this._TYPE = _TYPE;
        this._IMAGE = _IMAGE;
        this._IMAGECATEGORY = _IMAGECATEGORY;
        this._AUDIO = _AUDIO;
        this._DATEFULL = _DATEFULL;
    }

    public Double get_MONEY() {
        return _MONEY;
    }

    public void set_MONEY(Double _MONEY) {
        this._MONEY = _MONEY;
    }

    public String get_DESCRIPTION() {
        return _DESCRIPTION;
    }

    public void set_DESCRIPTION(String _DESCRIPTION) {
        this._DESCRIPTION = _DESCRIPTION;
    }

    public String get_DATE() {
        return _DATE;
    }

    public void set_DATE(String _DATE) {
        this._DATE = _DATE;
    }

    public String get_NAME() {
        return _NAME;
    }

    public void set_NAME(String _NAME) {
        this._NAME = _NAME;
    }

    public int get_TYPE() {
        return _TYPE;
    }

    public void set_TYPE(int _TYPE) {
        this._TYPE = _TYPE;
    }

    public String get_IMAGE() {
        return _IMAGE;
    }

    public void set_IMAGE(String _IMAGE) {
        this._IMAGE = _IMAGE;
    }

    public String get_AUDIO() {
        return _AUDIO;
    }

    public void set_AUDIO(String _AUDIO) {
        this._AUDIO = _AUDIO;
    }

    public String get_IMAGECATEGORY() {
        return _IMAGECATEGORY;
    }

    public void set_IMAGECATEGORY(String _IMAGECATEGORY) {
        this._IMAGECATEGORY = _IMAGECATEGORY;
    }
}
