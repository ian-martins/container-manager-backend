package com.example.demo.model;


public class Object_Image {

    private String Containers;
    private String CreatedAt;
    private String CreatedSince;
    private String Digest;
    private String ID;
    private String Repository;
    private String SharedSize;
    private String Size;
    private String Tag;
    private String UniqueSize;

    public Object_Image() {
    }

    public Object_Image(String Containers, String CreatedAt, String CreatedSince, String Digest, String ID, String Repository, String SharedSize, String Size, String Tag, String UniqueSize) {
        this.Containers = Containers;
        this.CreatedAt = CreatedAt;
        this.CreatedSince = CreatedSince;
        this.Digest = Digest;
        this.ID = ID;
        this.Repository = Repository;
        this.SharedSize = SharedSize;
        this.Size = Size;
        this.Tag = Tag;
        this.UniqueSize = UniqueSize;
    }

    public String getContainers() {
        return Containers;
    }

    public void setContainers(String Containers) {
        this.Containers = Containers;
    }

    public String getCreatedAt() {
        return CreatedAt;
    }

    public void setCreatedAt(String CreatedAt) {
        this.CreatedAt = CreatedAt;
    }

    public String getCreatedSince() {
        return CreatedSince;
    }

    public void setCreatedSince(String CreatedSince) {
        this.CreatedSince = CreatedSince;
    }

    public String getDigest() {
        return Digest;
    }

    public void setDigest(String Digest) {
        this.Digest = Digest;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getRepository() {
        return Repository;
    }

    public void setRepository(String Repository) {
        this.Repository = Repository;
    }

    public String getSharedSize() {
        return SharedSize;
    }

    public void setSharedSize(String SharedSize) {
        this.SharedSize = SharedSize;
    }

    public String getSize() {
        return Size;
    }

    public void setSize(String Size) {
        this.Size = Size;
    }

    public String getTag() {
        return Tag;
    }

    public void setTag(String Tag) {
        this.Tag = Tag;
    }

    public String getUniqueSize() {
        return UniqueSize;
    }

    public void setUniqueSize(String UniqueSize) {
        this.UniqueSize = UniqueSize;
    }
}
