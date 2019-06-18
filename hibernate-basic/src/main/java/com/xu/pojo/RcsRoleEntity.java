package com.xu.pojo;

/**
 * @author xuhongda on 2019/6/18
 * com.xu.pojo
 * hibernate-exe
 */
public class RcsRoleEntity {
    private int id;
    private String roleName;
    private String descri;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getDescri() {
        return descri;
    }

    public void setDescri(String descri) {
        this.descri = descri;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RcsRoleEntity that = (RcsRoleEntity) o;

        if (id != that.id) return false;
        if (roleName != null ? !roleName.equals(that.roleName) : that.roleName != null) return false;
        if (descri != null ? !descri.equals(that.descri) : that.descri != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (roleName != null ? roleName.hashCode() : 0);
        result = 31 * result + (descri != null ? descri.hashCode() : 0);
        return result;
    }
}
