/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ferru97.sketchfab.api;

import java.util.ArrayList;

/**
 *
 * @author Vito
 */
public class Model {
    
    private String uid = null;
    private String url = null;
    private String name = null;
    private ArrayList<String> tags = new ArrayList<>();
    private ArrayList<String> categories = new ArrayList<>();
    private ArrayList<String> comments = new ArrayList<>();
    private String date = null;
    private int like_count = 0;
    private int view_count = 0;
    private int comment_count = 0;
    private int vertex_count = 0;
    private int face_count = 0;
    private int sound_count = 0;
    
    public void Model(){}

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getTags() {
        return tags;
    }

    public void addTag(String tag) {
        this.tags.add(tag);
    }

    public ArrayList<String> getCategories() {
        return categories;
    }

    public void addCategory(String category) {
        this.categories.add(category);
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getLike_count() {
        return like_count;
    }

    public void setLike_count(int like_count) {
        this.like_count = like_count;
    }

    public int getView_count() {
        return view_count;
    }

    public void setView_count(int view_count) {
        this.view_count = view_count;
    }

    public int getComment_count() {
        return comment_count;
    }

    public void setComment_count(int comment_count) {
        this.comment_count = comment_count;
    }

    public int getVertex_count() {
        return vertex_count;
    }

    public void setVertex_count(int vertex_count) {
        this.vertex_count = vertex_count;
    }

    public int getFace_count() {
        return face_count;
    }

    public void setFace_count(int face_count) {
        this.face_count = face_count;
    }

    public int getSound_count() {
        return sound_count;
    }

    public void setSound_count(int sound_count) {
        this.sound_count = sound_count;
    }

    public ArrayList<String> getComments() {
        return comments;
    }
    
    public void add_Comment(String comment){
       this.comments.add(comment);
    }

    public void setComments(ArrayList<String> comments) {
        this.comments = comments;
    }
    
    

    @Override
    public String toString() {
        //Do not display comments
        return "Model{" + "uid=" + uid + ", url=" + url + ", name=" + name 
                + ", tags=" + tags + ", categories=" + categories + ", date=" 
                + date + ", like_count=" + like_count + ", view_count=" + view_count 
                + ", comment_count=" + comment_count + ", vertex_count=" + vertex_count 
                + ", face_count=" + face_count + ", sound_count=" + sound_count + '}';
    }
    
    public String getTableRecord(){
        StringBuilder encoded_comments = new StringBuilder();
        encoded_comments.append("\"");
        comments.stream().forEach(comment->encoded_comments.append(comment.replace("\"", "")+"\n"));
        encoded_comments.append("\"");
        
        StringBuilder encoded_tags = new StringBuilder();
        tags.stream().forEach(tag->encoded_tags.append(tag+","));
        if(encoded_tags.length()>0)
            encoded_tags.deleteCharAt(encoded_tags.length()-1);
        
        StringBuilder encoded_cat = new StringBuilder();
        categories.stream().forEach(category->encoded_cat.append(category+","));
        if(encoded_cat.length()>0)
            encoded_cat.deleteCharAt(encoded_cat.length()-1);
        
        return "\""+uid+"\";\"" + url + "\";\"" + name + "\";\"" + encoded_tags + "\";\"" 
                + encoded_cat + "\";\"" + date + "\";\"" + like_count + "\";\"" 
                + view_count + "\";\"" + comment_count + "\";\"" + vertex_count 
                + "\";\"" + face_count + "\";\"" + sound_count+"\";"+encoded_comments;
    }
    
    public static String getTableColumsHeader(){
        return "UID;URL;NAME;TAGS;CATEGORIES;DATE;LIKE_COUNT;VIEW_COUNT;COMMENT_COUNT;VERTEX_COUNT;FACE_COUNT;SOUND_COUNT;COMMENTS";
    }
    
    
}
