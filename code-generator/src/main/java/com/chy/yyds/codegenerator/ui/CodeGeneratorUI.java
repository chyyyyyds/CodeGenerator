package com.chy.yyds.codegenerator.ui;

import com.chy.yyds.codegenerator.generate.CodeGenerator;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;

public class CodeGeneratorUI {

   static final  String defaultOutputPath = "C:\\projectPath\\src\\main\\java\\your_package";


    private JTextField outputPathField;
    private JTextField authorField;
    private JCheckBox overrideFilesCheckBox;
    private JComboBox<String> idStrategyComboBox;
    private JComboBox<String> dateStrategyComboBox;
    private JCheckBox enableSwagger2CheckBox;

    private JTextField urlField;
    private JComboBox<String> dbDriverComboBox;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JTextField tablePrefixField;
    private JTextField tableNamesField;

    private JTextField entityPackageField;
    private JTextField mapperPackageField;
    private JTextField servicePackageField;
    private JTextField serviceImplPackageField;
    private JTextField controllerPackageField;
    private JTextField xmlOutputFolderField;

    private JButton generateButton;

    public CodeGeneratorUI() {
        JFrame frame = new JFrame("代码生成器配置");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 600);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        // 数据源配置
        JPanel dataSourcePanel = new JPanel(new GridLayout(6, 2));
        dataSourcePanel.setBorder(BorderFactory.createTitledBorder("数据源配置"));

        dataSourcePanel.add(new JLabel("数据库URL:"));
        urlField = new JTextField("jdbc:mysql://localhost:3306/database");
        dataSourcePanel.add(urlField);

        dataSourcePanel.add(new JLabel("数据库驱动:"));
        dbDriverComboBox = new JComboBox<>(new String[]{"com.mysql.cj.jdbc.Driver", "org.postgresql.Driver"});
        dbDriverComboBox.setSelectedIndex(0); // 默认为 MySQL
        dataSourcePanel.add(dbDriverComboBox);

        dataSourcePanel.add(new JLabel("用户名:"));
        usernameField = new JTextField("root");
        dataSourcePanel.add(usernameField);

        dataSourcePanel.add(new JLabel("密码:"));
        passwordField = new JPasswordField("root");
        dataSourcePanel.add(passwordField);

        dataSourcePanel.add(new JLabel("表前缀(如表名't_user', 前缀't',生成的实体类名会是'User'):"));
        tablePrefixField = new JTextField();
        dataSourcePanel.add(tablePrefixField);

        dataSourcePanel.add(new JLabel("要生成的表名(多个表之间英文逗号分隔):"));
        tableNamesField = new JTextField();
        dataSourcePanel.add(tableNamesField);

        mainPanel.add(dataSourcePanel);

        // 输出路径配置
        JPanel pathPanel = new JPanel(new GridLayout(1, 2));
        pathPanel.setBorder(BorderFactory.createTitledBorder("输出路径"));
        pathPanel.add(new JLabel("输出路径:"));
        outputPathField = new JTextField(defaultOutputPath);
        outputPathField.setPreferredSize(new Dimension(250, 10));  // 控制输入框的高度
        outputPathField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updatePackagePaths();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updatePackagePaths();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updatePackagePaths();
            }
        });
        pathPanel.add(outputPathField);

        mainPanel.add(pathPanel);

        // 包配置联动输出路径
        JPanel packagePanel = new JPanel(new GridLayout(6, 2));
        packagePanel.setBorder(BorderFactory.createTitledBorder("包配置"));

        packagePanel.add(new JLabel("实体类包路径:"));
        entityPackageField = new JTextField(defaultOutputPath + "\\entity");
        packagePanel.add(entityPackageField);

        packagePanel.add(new JLabel("Mapper包路径:"));
        mapperPackageField = new JTextField(defaultOutputPath + "\\mapper");
        packagePanel.add(mapperPackageField);

        packagePanel.add(new JLabel("Service包路径:"));
        servicePackageField = new JTextField(defaultOutputPath + "\\service");
        packagePanel.add(servicePackageField);

        packagePanel.add(new JLabel("ServiceImpl包路径:"));
        serviceImplPackageField = new JTextField(defaultOutputPath + "\\service\\impl");
        packagePanel.add(serviceImplPackageField);

        packagePanel.add(new JLabel("Controller包路径:"));
        controllerPackageField = new JTextField(defaultOutputPath + "\\controller");
        packagePanel.add(controllerPackageField);

        packagePanel.add(new JLabel("SQL XML输出路径:"));
        xmlOutputFolderField = new JTextField(defaultOutputPath + "\\src\\main\\resources\\mapper");
        packagePanel.add(xmlOutputFolderField);

        mainPanel.add(packagePanel);

        // 作者、文件覆盖、ID策略、日期策略、Swagger2配置
        JPanel configPanel = new JPanel(new GridLayout(5, 2));
        configPanel.setBorder(BorderFactory.createTitledBorder("配置"));

        configPanel.add(new JLabel("作者:"));
        authorField = new JTextField();
        configPanel.add(authorField);

        configPanel.add(new JLabel("是否覆盖原有文件:"));
        overrideFilesCheckBox = new JCheckBox();
        configPanel.add(overrideFilesCheckBox);

        configPanel.add(new JLabel("ID生成策略:"));
        idStrategyComboBox = new JComboBox<>(new String[]{"INPUT", "AUTO", "ASSIGN_ID", "NONE"});
        configPanel.add(idStrategyComboBox);

        configPanel.add(new JLabel("日期生成策略:"));
        dateStrategyComboBox = new JComboBox<>(new String[]{"ONLY_DATE", "TIME_PACK"});
        dateStrategyComboBox.setSelectedIndex(1); // 默认为 Java 8的新时间类型
        configPanel.add(dateStrategyComboBox);

        configPanel.add(new JLabel("是否开启Swagger2:"));
        enableSwagger2CheckBox = new JCheckBox();
        configPanel.add(enableSwagger2CheckBox);

        mainPanel.add(configPanel);

        // 生成按钮
        generateButton = new JButton("生成代码");
        generateButton.addActionListener(e -> generateCode());
        mainPanel.add(generateButton);

        frame.add(mainPanel);
        frame.setVisible(true);
    }

    private void updatePackagePaths() {
        String outputPath = outputPathField.getText();
        if (!outputPath.endsWith("\\")) {
            outputPath += "\\";
        }

        entityPackageField.setText(outputPath + "entity");
        mapperPackageField.setText(outputPath + "mapper");
        servicePackageField.setText(outputPath + "service");
        serviceImplPackageField.setText(outputPath + "service\\impl");
        controllerPackageField.setText(outputPath + "controller");
        xmlOutputFolderField.setText(outputPath + "src/main/resources/mapper");
    }

    private void generateCode() {
        String outputPath = outputPathField.getText();
        String author = authorField.getText();
        boolean overrideFiles = overrideFilesCheckBox.isSelected();
        String idStrategy = idStrategyComboBox.getSelectedItem().toString();
        String dateStrategy = dateStrategyComboBox.getSelectedItem().toString();
        boolean enableSwagger2 = enableSwagger2CheckBox.isSelected();

        String url = urlField.getText();
        String dbDriver = dbDriverComboBox.getSelectedItem().toString();
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        String tablePrefix = tablePrefixField.getText();
        String tableNames = tableNamesField.getText();

        String entityPackage = entityPackageField.getText();
        String mapperPackage = mapperPackageField.getText();
        String servicePackage = servicePackageField.getText();
        String serviceImplPackage = serviceImplPackageField.getText();
        String controllerPackage = controllerPackageField.getText();
        String xmlOutputFolder = xmlOutputFolderField.getText();

        // 使用配置生成代码
        try {
            CodeGenerator generator = new CodeGenerator();
            generator.generateCode(outputPath, outputPath, author, overrideFiles, idStrategy, dateStrategy, enableSwagger2, url, dbDriver, username, password, tablePrefix, tableNames.split(","), entityPackage, mapperPackage, servicePackage, serviceImplPackage, controllerPackage, xmlOutputFolder);
            JOptionPane.showMessageDialog(null, "代码生成完成！", "成功", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "生成失败：" + ex.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        new CodeGeneratorUI();
    }
}
