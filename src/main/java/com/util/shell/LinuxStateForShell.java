/*
 * Written by wei.Li and released to the public domain
 * Welcome to correct discussion as explained at
 *
 * -----------------------------------------------------------------
 *
 * GitHub:  https://github.com/GourdErwa
 * Blog  :	http://blog.csdn.net/xiaohulunb
 * WeiBo :	http://www.weibo.com/xiaohulunb  	@GourdErwa
 * Email :	gourderwa@163.com
 */

package com.util.shell;


import com.jcraft.jsch.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * 远程调用Linux shell 命令
 *
 * @author wei.Li by 14-9-2.
 */
public class LinuxStateForShell {

    public static final String[] COMMANDS = {"top -b -n 1", "df -hl"};
    public static final String LINE_SEPARATOR = System.getProperty("line.separator");
    private static Session session;

    /**
     * 连接到指定的HOST
     *
     * @return isConnect
     * @throws JSchException JSchException
     */
    private static boolean connect(String user, String passwd, String host) {
        JSch jsch = new JSch();
        try {
            session = jsch.getSession(user, host, 22);
            session.setPassword(passwd);

            java.util.Properties config = new java.util.Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);

            session.connect();
        } catch (JSchException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 远程连接Linux 服务器 执行相关的命令
     *
     * @param commands 执行的脚本
     * @param user     远程连接的用户名
     * @param passwd   远程连接的密码
     * @param host     远程连接的主机IP
     * @return 最终命令返回信息
     */
    public static Map<String, String> runDistanceShell(String[] commands, String user, String passwd, String host) {
        if (!connect(user, passwd, host)) {
            return null;
        }
        Map<String, String> map = new HashMap<>();
        StringBuilder stringBuffer;

        BufferedReader reader = null;
        Channel channel = null;
        try {
            for (String command : commands) {
                stringBuffer = new StringBuilder();
                channel = session.openChannel("exec");
                ((ChannelExec) channel).setCommand(command);

                channel.setInputStream(null);
                ((ChannelExec) channel).setErrStream(System.err);

                channel.connect();
                InputStream in = channel.getInputStream();
                reader = new BufferedReader(new InputStreamReader(in));
                String buf;
                while ((buf = reader.readLine()) != null) {

                    /*//舍弃PID 进程信息
                    if (buf.contains("PID")) {
                        break;
                    }*/
                    stringBuffer.append(buf.trim()).append(LINE_SEPARATOR);
                }
                //每个命令存储自己返回数据-用于后续对返回数据进行处理
                map.put(command, stringBuffer.toString());
            }
        } catch (IOException | JSchException e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (channel != null) {
                channel.disconnect();
            }
            session.disconnect();
        }
        return map;
    }


    /**
     * 直接在本地执行 shell
     *
     * @param commands 执行的脚本
     * @return 执行结果信息
     */
    public static Map<String, String> runLocalShell(String[] commands) {
        Runtime runtime = Runtime.getRuntime();

        Map<String, String> map = new HashMap<>();
        StringBuilder stringBuffer;

        BufferedReader reader;
        Process process;
        for (String command : commands) {
            stringBuffer = new StringBuilder();
            try {
                process = runtime.exec(command);
                InputStream inputStream = process.getInputStream();
                reader = new BufferedReader(new InputStreamReader(inputStream));
                String buf;
                while ((buf = reader.readLine()) != null) {
                    /*//舍弃PID 进程信息
                    if (buf.contains("PID")) {
                        break;
                    }*/
                    stringBuffer.append(buf.trim()).append(LINE_SEPARATOR);
                }

            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
            //每个命令存储自己返回数据-用于后续对返回数据进行处理
            map.put(command, stringBuffer.toString());
        }
        return map;
    }


    /**
     * 处理 shell 返回的信息
     * <p>
     * 具体处理过程以服务器返回数据格式为准
     * 不同的Linux 版本返回信息格式不同
     *
     * @param result shell 返回的信息
     * @return 最终处理后的信息
     */
    private static String disposeResultMessage(Map<String, String> result) {

        StringBuilder buffer = new StringBuilder();

        for (String command : COMMANDS) {
            String commandResult = result.get(command);
            if (null == commandResult) continue;
            if (command.equals("top -b -n 1")) {
                String[] strings = commandResult.split(LINE_SEPARATOR);
                //将返回结果按换行符分割
                for (String line : strings) {
                    if (line.contains("Cpu(s):")) {
                        //处理CPU Cpu(s): 10.8%us,  0.9%sy,  0.0%ni, 87.6%id,  0.7%wa,  0.0%hi,  0.0%si,  0.0%st

                    } else if (line.contains("Mem")) {
                        //处理内存 Mem:  66100704k total, 65323404k used,   777300k free,    89940k buffers

                    }
                }
            } else if (command.equals("df -nl")) {
                //处理系统磁盘状态
                /**
                 Filesystem            Size  Used Avail Use% Mounted on
                 /dev/sda3             442G  327G   93G  78% /
                 tmpfs                  32G     0   32G   0% /dev/shm
                 /dev/sda1             788M   60M  689M   8% /boot
                 /dev/md0              1.9T  483G  1.4T  26% /ezsonar
                 */

            }

        }

        return buffer.toString();
    }

    public static void main(String[] args) {
        Map<String, String> result = runDistanceShell(COMMANDS, "root", "root", "192.168.1.23");
        System.out.println(result);
        //runLocalShell(COMMANDS);
    }

    /**
     * 执行测试结果
     *
     ~~~~~~~~~~ ~~~~~~~~~~ ~~~~~~~~~~ ~~~~~~~~~~ ~~~~~~~~~~ ~~~~~~~~~~ ~~~~~~~~~~
     top - 13:06:06 up 32 days, 23:28,  0 users,  load average: 1.47, 1.70, 1.74
     Tasks: 171 total,   1 running, 170 sleeping,   0 stopped,   0 zombie
     Cpu(s): 10.8%us,  0.9%sy,  0.0%ni, 87.6%id,  0.7%wa,  0.0%hi,  0.0%si,  0.0%st
     Mem:  66100704k total, 65323404k used,   777300k free,    89940k buffers
     Swap: 16777212k total,   396056k used, 16381156k free, 23461308k cached

     PID USER      PR  NI  VIRT  RES  SHR S %CPU %MEM    TIME+  COMMAND
     13673 root      20   0  159g  27g 2.2g S 204.1 43.2  10903:09 java


     Filesystem            Size  Used Avail Use% Mounted on
     /dev/sda3             442G  327G   93G  78% /
     tmpfs                  32G     0   32G   0% /dev/shm
     /dev/sda1             788M   60M  689M   8% /boot
     /dev/md0              1.9T  483G  1.4T  26% /ezsonar
     */

    /**
     * 字段说明
     *
     * 统计信息区
     前五行是系统整体的统计信息。第一行是任务队列信息，同 uptime 命令的执行结果。其内容如下
     ：
     01:06:48           当前时间
     up 1:22            系统运行时间，格式为时:分
     1 user             当前登录用户数
     load average: 0.06, 0.60, 0.48 系统负载，即任务队列的平均长度。
     三个数值分别为 1分钟、5分钟、15分钟前到现在的平均值。

     第二、三行为进程和CPU的信息。
     当有多个CPU时，这些内容可能会超过两行。内容如下：
     Tasks: 29 total    进程总数
     1 running          正在运行的进程数
     28 sleeping        睡眠的进程数
     0 stopped          停止的进程数
     0 zombie           僵尸进程数
     Cpu(s): 0.3% us    用户空间占用CPU百分比
     1.0% sy            内核空间占用CPU百分比
     0.0% ni            用户进程空间内改变过优先级的进程占用CPU百分比
     98.7% id           空闲CPU百分比
     0.0% wa            等待输入输出的CPU时间百分比
     0.0% hi            CPU服务于硬中断所耗费的时间总额
     0.0% si，st         CPU服务于软中断所耗费的时间总额、Steal Time

     最后两行为内存信息。内容如下：
     Mem: 191272k total 物理内存总量
     173656k used       使用的物理内存总量
     17616k free        空闲内存总量
     22052k buffers     用作内核缓存的内存量
     Swap: 192772k total 交换区总量
     0k used            使用的交换区总量
     192772k free       空闲交换区总量
     123988k cached     缓冲的交换区总量。
     内存中的内容被换出到交换区，而后又被换入到内存，但使用过的交换区尚未被覆盖，
     该数值即为这些内容已存在于内存中的交换区的大小。
     相应的内存再次被换出时可不必再对交换区写入。



     进程信息区
     统计信息区域的下方显示了各个进程的详细信息。
     序号 列名 含义
     a PID      进程id
     b PPID     父进程id
     c RUSER Real user name
     d UID      进程所有者的用户id
     e USER     进程所有者的用户名
     f GROUP    进程所有者的组名
     g TTY      启动进程的终端名。不是从终端启动的进程则显示为 ?
     h PR       优先级
     i NI nice值。负值表示高优先级，正值表示低优先级
     j P        最后使用的CPU，仅在多CPU环境下有意义
     k %CPU     上次更新到现在的CPU时间占用百分比
     l TIME     进程使用的CPU时间总计，单位秒
     m TIME+    进程使用的CPU时间总计，单位1/100秒
     n %MEM     进程使用的物理内存百分比
     o VIRT     进程使用的虚拟内存总量，单位kb。VIRT=SWAP+RES
     p SWAP     进程使用的虚拟内存中，被换出的大小，单位kb。
     q RES      进程使用的、未被换出的物理内存大小，单位kb。RES=CODE+DATA
     r CODE     可执行代码占用的物理内存大小，单位kb
     s DATA     可执行代码以外的部分(数据段+栈)占用的物理内存大小，单位kb
     t SHR      共享内存大小，单位kb
     u nFLT     页面错误次数
     v nDRT     最后一次写入到现在，被修改过的页面数。
     w S        进程状态。
     D=不可中断的睡眠状态
     R=运行
     S=睡眠
     T=跟踪/停止
     Z=僵尸进程
     x COMMAND 命令名/命令行
     y WCHAN 若该进程在睡眠，则显示睡眠中的系统函数名
     */
}
