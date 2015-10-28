package com.athena.stadium.security;

import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.sql.DataSource;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 实现SpringSecurity的UserDetailsService接口,实现获取用户Detail信息的回调函数.
 *
 * Created by Lingfeng on 2015/10/27.
 */
public class StadiumUserDetailsService extends JdbcDaoSupport implements UserDetailsService {

    public StadiumUserDetailsService() {
    }

    public StadiumUserDetailsService(DataSource dataSource) {
        setDataSource(dataSource);
    }

    /**
     * 获取用户Details信息的回调函数.
     *
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<StadiumUser> users = loadUsersByUsername(username);

        if (users.isEmpty()) {
            logger.debug("Query returned no results for user '" + username + "'");
            throw new UsernameNotFoundException("User not fount for " + username);
        }

        StadiumUser user = users.get(0);
        Set<GrantedAuthority> grantedAuths = loadUserAuthoritiesById(user.getId());

        if (grantedAuths.size() == 0) {
            logger.debug("User '" + username + "' has no authorities and will be treated as 'not found'");
            throw new UsernameNotFoundException("No GratedAuthority with user " + user.getUsername());
        }

        return new StadiumUser(user.getId(), user.getUsername(), user.getMobilePhone(),
                user.getPassword(), user.isEnabled(), grantedAuths);
    }

    /**
     * 数据库加载用户信息
     *
     * @param username
     * @return
     */
    protected List<StadiumUser> loadUsersByUsername(String username) {
        return getJdbcTemplate().query(
                "SELECT id, username, mobile_phone, password, enabled FROM users WHERE username = ? OR mobile_phone = ?",
                new String[] {username, username},
                (rs, rowNum) -> {
                    long id = rs.getLong(1);
                    String name = rs.getString(2);
                    String mobile = rs.getString(3);
                    String pwd = rs.getString(4);
                    boolean enabled = rs.getBoolean(5);

                    return new StadiumUser(id, name, mobile, pwd, enabled, AuthorityUtils.NO_AUTHORITIES);
                }
        );
    }

    /**
     * 获得用户所有角色的权限集合
     *
     * @param id
     * @return
     */
    protected Set<GrantedAuthority> loadUserAuthoritiesById(long id) {
        Set<GrantedAuthority> authSet = new HashSet<>();

        List<GrantedAuthority> result = getJdbcTemplate().query(
                "SELECT id, authority FROM authorities WHERE id = ?",
                new Long[] {id},
                (rs, rowNum) -> {
                    String roleName = rs.getString(2);

                    return new SimpleGrantedAuthority(roleName);
                });

        authSet.addAll(result);

        return authSet;
    }
}
