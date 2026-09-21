package hospital.service;

import hospital.util.*;
import java.util.*;

/** Reusable service for small CSV tables whose first column is an ID. */
public class TableService {
    protected final String file,prefix,title; protected final String[] fields;
    public TableService(String file,String prefix,String title,String...fields){this.file=file;this.prefix=prefix;this.title=title;this.fields=fields;}
    public List<String[]> rows(){return CsvUtil.read(file);}
    public void add(){String[] row=new String[fields.length+1];row[0]=IdGenerator.next(file,prefix);for(int i=0;i<fields.length;i++)row[i+1]=Input.text(fields[i]+": ");CsvUtil.append(file,row);System.out.println(title+" saved. ID: "+row[0]);}
    public void view(){print(rows());}
    public void search(){String term=Input.text("Search text: ").toLowerCase();List<String[]>found=new ArrayList<>();for(String[]r:rows())if(String.join(" ",r).toLowerCase().contains(term))found.add(r);print(found);}
    public void delete(){String id=Input.text(title+" ID to delete: ");List<String[]>keep=new ArrayList<>();boolean found=false;for(String[]r:rows())if(r.length>0&&r[0].equalsIgnoreCase(id))found=true;else keep.add(r);CsvUtil.writeAll(file,keep);System.out.println(found?"Deleted.":"ID not found.");}
    protected void print(List<String[]> list){if(list.isEmpty()){System.out.println("No "+title.toLowerCase()+" records found.");return;}for(String[]r:list)System.out.println(format(r));}
    protected String format(String[] r){StringBuilder s=new StringBuilder();for(int i=0;i<r.length;i++){if(i>0)s.append(" | ");s.append(i==0?"ID":fields[i-1]).append(": ").append(r[i]);}return s.toString();}
}
