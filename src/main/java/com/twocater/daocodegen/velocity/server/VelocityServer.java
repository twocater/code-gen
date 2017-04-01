package com.twocater.daocodegen.velocity.server;

import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.twocater.daocodegen.velocity.util.DbUtil;
import com.twocater.daocodegen.velocity.util.ObjectUtil;
import com.twocater.daocodegen.velocity.vo.Column;
import com.twocater.daocodegen.velocity.vo.Index;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;

public class VelocityServer {
    private VelocityContext context = null;
    private Template template = null;

    public void init(String properties) throws Exception {
        if (properties != null && properties.trim().length() > 0) {
            Velocity.init(properties);
        } else {
            Properties p = new Properties();
            p.setProperty("resource.loader", "class");
            p.setProperty("class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
            // VelocityEngine ve = new VelocityEngine();
            // ve.init(p);

            Velocity.init(p);
        }
        context = new VelocityContext();
    }

    public void init(Properties properties) throws Exception {
        Velocity.init(properties);
        context = new VelocityContext();
    }

    public void put(String key, Object value) {
        context.put(key, value);
    }

    public void setTemplate(String templateFile) throws IllegalStateException {
        try {
            template = Velocity.getTemplate(templateFile);
        } catch (ResourceNotFoundException rnfe) {
            rnfe.printStackTrace();
            throw new IllegalStateException(" error : cannot find template " + templateFile);
        } catch (ParseErrorException pee) {
            throw new IllegalStateException(" Syntax error in template " + templateFile + ":" + pee);
        } catch (Exception e) {
            throw new IllegalStateException(e.toString());
        }

    }

    public void setTemplate(String templateFile, String characterSet) throws IllegalStateException {
        try {
            template = Velocity.getTemplate(templateFile, characterSet);
        } catch (ResourceNotFoundException rnfe) {
            rnfe.printStackTrace();
            throw new IllegalStateException(" error : cannot find template " + templateFile);
        } catch (ParseErrorException pee) {
            throw new IllegalStateException(" Syntax error in template " + templateFile + ":" + pee);
        } catch (Exception e) {
            throw new IllegalStateException(e.toString());
        }

    }

    /**
     * 转换为文本文件
     */
    public String toText() throws IllegalStateException {
        StringWriter sw = new StringWriter();
        try {
            template.merge(context, sw);
        } catch (Exception e) {
            throw new IllegalStateException(e.toString());
        }
        return sw.toString();
    }

    public void toFile(String fileName) throws IllegalStateException {
        try {
            StringWriter sw = new StringWriter();
            template.merge(context, sw);
            PrintWriter filewriter = new PrintWriter(new FileOutputStream(fileName), true);
            filewriter.println(sw.toString());
            filewriter.close();
        } catch (Exception e) {
            throw new IllegalStateException(e.toString());
        }

    }

    public String createDao(String vmFile, String table, String packageStr, List<Column> columns, Map<String, List<String>> uniqueIndexList, List<String> autoIncrementColumns, Map<String, Column> columnMap, String databaseName) {
        setTemplate(vmFile);
        context.put("package", packageStr);
        context.put("table", table);
        String vo = ObjectUtil.getVONameByTableName(table);
        context.put("vo", vo);
        String dao = ObjectUtil.getDAONameByTableName(table);
        context.put("dao", dao);
        String object = ObjectUtil.getObjectName(vo);
        context.put("object", object);
        String entry = ObjectUtil.getEntryNmaeByTableName(table);
        context.put("entry", entry);
        String rowmapper = ObjectUtil.getRowMapperNmaeByTableName(table);
        context.put("rowmapper", rowmapper);
        context.put("columns", columns);
        context.put("uniqueIndexList", uniqueIndexList);
        context.put("columnType", DbUtil.getColumnType(columns));
        context.put("autoIncrementColumns", autoIncrementColumns);
        context.put("columnMap", columnMap);
        context.put("databaseName", databaseName);
        return toText();
    }

    public String createServlet(String vmFile, String table, String packageStr, List<Column> columns) {
        setTemplate(vmFile);
        context.put("package", packageStr);
        context.put("table", table);
        context.put("servlet", ObjectUtil.getServletNameByTableName(table));
        String vo = ObjectUtil.getVONameByTableName(table);
        context.put("vo", vo);
        String dao = ObjectUtil.getDAONameByTableName(table);
        context.put("dao", dao);
        String object = ObjectUtil.getObjectName(vo);
        context.put("object", object);
        context.put("columns", columns);

        return toText();
    }

    public String createDaoFactory(String vmFile, String packageStr, Map<String, String> daoMap) {
        setTemplate(vmFile);
        context.put("package", packageStr);
        context.put("daoMap", daoMap);
        return toText();
    }

    public String createMappingServer(String mappingvm, String packageStr, Map<String, List<Column>> mappingMap) {
        setTemplate(mappingvm);
        context.put("package", packageStr);
        context.put("mappingMap", mappingMap);
        return toText();
    }

    public String createServer(String vmFile, String table, String packageStr, List<Column> columns) {
        setTemplate(vmFile);
        context.put("package", packageStr);
        String clazz = ObjectUtil.getEntryNmaeByTableName(table);
        context.put("clazz", clazz);
        String object = ObjectUtil.getObjectName(clazz);
        context.put("object", object);
        return toText();
    }

    public String createVo(String vmFile, String table, String packageStr, List<Column> columns) {
        setTemplate(vmFile);
        context.put("package", packageStr);
        String vo = ObjectUtil.getVONameByTableName(table);
        context.put("vo", vo);
        context.put("columns", columns);
        return toText();
    }

    public String createVoByProtocol(String vmFile, String table, String packageStr, List<Column> columns) {
        setTemplate(vmFile);
        context.put("package", packageStr);
        String message = ObjectUtil.getEntryNmaeByTableName(table);
        context.put("message", message);
        context.put("columns", columns);
        return toText();
    }

    public String createRowMapper(String vmFile, String table, String packageStr, List<Column> columns) {
        setTemplate(vmFile);
        context.put("package", packageStr);
        String vo = ObjectUtil.getVONameByTableName(table);
        context.put("vo", vo);
        String entry = ObjectUtil.getEntryNmaeByTableName(table);
        context.put("entry", entry);
        String object = ObjectUtil.getObjectName(vo);
        context.put("object", object);
        context.put("columns", columns);
        return toText();
    }

}
