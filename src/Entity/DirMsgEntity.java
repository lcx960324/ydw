package Entity;

import javax.persistence.*;

/**
 * Created by tend on 2017/7/7.
 */
@Entity
@Table(name = "dir_msg", schema = "ydw_uploader", catalog = "")
public class DirMsgEntity {
    private int fileId;
    private String path;
    private int isDir;
    private String user;
    private int isDel;
    private String time;
    private String deltime;
    private String deluser;
    private String fname;
    private String linked;
    private Long fileSize;

    @Id
    @Column(name = "fileId")
    public int getFileId() {
        return fileId;
    }

    public void setFileId(int fileId) {
        this.fileId = fileId;
    }

    @Basic
    @Column(name = "path")
    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Basic
    @Column(name = "isDir")
    public int getIsDir() {
        return isDir;
    }

    public void setIsDir(int isDir) {
        this.isDir = isDir;
    }

    @Basic
    @Column(name = "user")
    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    @Basic
    @Column(name = "isDel")
    public int getIsDel() {
        return isDel;
    }

    public void setIsDel(int isDel) {
        this.isDel = isDel;
    }

    @Basic
    @Column(name = "time")
    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Basic
    @Column(name = "deltime")
    public String getDeltime() {
        return deltime;
    }

    public void setDeltime(String deltime) {
        this.deltime = deltime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DirMsgEntity that = (DirMsgEntity) o;

        if (fileId != that.fileId) return false;
        if (isDir != that.isDir) return false;
        if (isDel != that.isDel) return false;
        if (path != null ? !path.equals(that.path) : that.path != null) return false;
        if (user != null ? !user.equals(that.user) : that.user != null) return false;
        if (time != null ? !time.equals(that.time) : that.time != null) return false;
        if (deltime != null ? !deltime.equals(that.deltime) : that.deltime != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = fileId;
        result = 31 * result + (path != null ? path.hashCode() : 0);
        result = 31 * result + isDir;
        result = 31 * result + (user != null ? user.hashCode() : 0);
        result = 31 * result + isDel;
        result = 31 * result + (time != null ? time.hashCode() : 0);
        result = 31 * result + (deltime != null ? deltime.hashCode() : 0);
        return result;
    }

    @Basic
    @Column(name = "deluser")
    public String getDeluser() {
        return deluser;
    }

    public void setDeluser(String deluser) {
        this.deluser = deluser;
    }

    @Basic
    @Column(name = "fname")
    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    @Basic
    @Column(name = "linked")
    public String getLinked() {
        return linked;
    }

    public void setLinked(String linked) {
        this.linked = linked;
    }

    @Basic
    @Column(name = "fileSize")
    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }
}
