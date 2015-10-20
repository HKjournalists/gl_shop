package com.appabc.datas.cms.dao;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by zouxifeng on 5/5/15.
 */
public class ServiceLogTemplateResolver extends NamedParameterJdbcDaoSupport {

    public static final String MATCH_CONTRACT_FAIL = "MCF";

    private static final String QUERY_SQL = "select * from SYS_SERVICE_LOG_TEMPLATES" +
            " where id=:id";

    public String resolve(String id, Map<String, Object> params) {
        SqlParameterSource param = new MapSqlParameterSource("id", id);
        String template = this.getNamedParameterJdbcTemplate().queryForObject(QUERY_SQL,
                param, String.class);
        return regexResolve(template, params);
    }

    private String regexResolve(String template, Map<String, Object> params) {
        Pattern pattern = Pattern.compile("\\{(.+?)\\}");
        Matcher matcher = pattern.matcher(template);
        StringBuilder builder = new StringBuilder();
        int i = 0;
        while (matcher.find()) {
            Object replacement = params.get(matcher.group(1));
            builder.append(template.substring(i, matcher.start()));
            if (replacement == null) {
                builder.append(matcher.group(0));
            } else {
                builder.append(replacement);
            }
            i = matcher.end();
        }
        builder.append(template.substring(i, template.length()));
        return builder.toString();
    }
}
