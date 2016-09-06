package com.example.rujuls.datafeed;

/**
 * Created by RUJUL S on 29-08-2016.
 */
public class DataFields {

    public int _id;
    public String _name;
    public long _phone;
    public String _mail;
    public String _address;

    public DataFields(String name, long phone, String mail, String address){

        this._name = name;
        this._phone = phone;
        this._mail = mail;
        this._address = address;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String get_name() {
        return _name;
    }

    public void set_name(String _name) {
        this._name = _name;
    }

    public long get_phone() {
        return _phone;
    }

    public void set_phone(long _phone) {
        this._phone = _phone;
    }

    public String get_mail() {
        return _mail;
    }

    public void set_mail(String _mail) {
        this._mail = _mail;
    }

    public String get_address() {
        return _address;
    }

    public void set_address(String _address) {
        this._address = _address;
    }
}
