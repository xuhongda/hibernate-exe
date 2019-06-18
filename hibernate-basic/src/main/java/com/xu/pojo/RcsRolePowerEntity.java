package com.xu.pojo;

/**
 * @author xuhongda on 2019/6/18
 * com.xu.pojo
 * hibernate-exe
 */
public class RcsRolePowerEntity {
    private int id;
    private int roleId;
    private int powerId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public int getPowerId() {
        return powerId;
    }

    public void setPowerId(int powerId) {
        this.powerId = powerId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RcsRolePowerEntity that = (RcsRolePowerEntity) o;

        if (id != that.id) return false;
        if (roleId != that.roleId) return false;
        if (powerId != that.powerId) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + roleId;
        result = 31 * result + powerId;
        return result;
    }
}
