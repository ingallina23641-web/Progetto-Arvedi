import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

/**
 * GUI principale per il Gestionale Cabine Elettriche.
 * Sostituisce il Main.java testuale.
 * Usare: tasto destro sul progetto NetBeans → Run File su questo file.
 */
public class MainGUI extends JFrame {

    // ── Gestori logica di business ──────────────────────────────────────────
    private final GestoreCabine    gestoreCabine    = new GestoreCabine();
    private final GestoreQuadri    gestoreQuadri    = new GestoreQuadri();
    private final GestoreTecnici   gestoreTecnici   = new GestoreTecnici();
    private final GestoreInterventi gestoreInterventi = new GestoreInterventi();

    // ── Palette colori ──────────────────────────────────────────────────────
    private static final Color BG_DARK    = new Color(18,  26,  38);
    private static final Color BG_PANEL   = new Color(26,  38,  56);
    private static final Color BG_CARD    = new Color(34,  50,  72);
    private static final Color ACCENT     = new Color(0,  168, 232);
    private static final Color ACCENT2    = new Color(0,  220, 180);
    private static final Color TEXT_MAIN  = new Color(220, 235, 255);
    private static final Color TEXT_SUB   = new Color(130, 160, 200);
    private static final Color BORDER_COL = new Color(50,  72, 105);
    private static final Color SUCCESS    = new Color(60,  200, 120);
    private static final Color WARNING    = new Color(255, 180,  50);
    private static final Color DANGER     = new Color(220,  70,  70);

    // ── Tabelle ─────────────────────────────────────────────────────────────
    private DefaultTableModel modelCabine;
    private DefaultTableModel modelQuadri;
    private DefaultTableModel modelTecnici;
    private DefaultTableModel modelInterventi;

    // ══════════════════════════════════════════════════════════════════════
    public MainGUI() {
        setTitle("⚡ Gestionale Cabine Elettriche");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1100, 720);
        setMinimumSize(new Dimension(900, 600));
        setLocationRelativeTo(null);
        getContentPane().setBackground(BG_DARK);

        JPanel root = new JPanel(new BorderLayout(0, 0));
        root.setBackground(BG_DARK);

        root.add(buildHeader(), BorderLayout.NORTH);
        root.add(buildTabs(),   BorderLayout.CENTER);

        setContentPane(root);
        setVisible(true);
    }

    // ══════════════════════════════════════════════════════════════════════
    //  HEADER
    // ══════════════════════════════════════════════════════════════════════
    private JPanel buildHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(BG_PANEL);
        header.setBorder(new MatteBorder(0, 0, 2, 0, ACCENT));
        header.setPreferredSize(new Dimension(0, 64));

        JLabel logo = new JLabel("  ⚡ GESTIONALE CABINE ELETTRICHE");
        logo.setFont(new Font("Monospaced", Font.BOLD, 20));
        logo.setForeground(ACCENT);

        JLabel version = new JLabel("v1.0  ");
        version.setFont(new Font("Monospaced", Font.PLAIN, 12));
        version.setForeground(TEXT_SUB);

        header.add(logo,    BorderLayout.WEST);
        header.add(version, BorderLayout.EAST);
        return header;
    }

    // ══════════════════════════════════════════════════════════════════════
    //  TAB PRINCIPALE
    // ══════════════════════════════════════════════════════════════════════
    private JTabbedPane buildTabs() {
        JTabbedPane tabs = new JTabbedPane();
        tabs.setBackground(BG_DARK);
        tabs.setForeground(TEXT_MAIN);
        tabs.setFont(new Font("Monospaced", Font.BOLD, 13));

        // Forza stile custom sui tab
        UIManager.put("TabbedPane.selected",             BG_CARD);
        UIManager.put("TabbedPane.background",           BG_PANEL);
        UIManager.put("TabbedPane.foreground",           TEXT_MAIN);
        UIManager.put("TabbedPane.contentAreaColor",     BG_DARK);
        UIManager.put("TabbedPane.tabAreaBackground",    BG_PANEL);
        UIManager.put("TabbedPane.focus",                ACCENT);

        tabs.addTab("🏭  Cabine",      buildTabCabine());
        tabs.addTab("⚙️  Quadri",      buildTabQuadri());
        tabs.addTab("👷  Tecnici",     buildTabTecnici());
        tabs.addTab("📋  Interventi",  buildTabInterventi());

        return tabs;
    }

    // ══════════════════════════════════════════════════════════════════════
    //  TAB CABINE
    // ══════════════════════════════════════════════════════════════════════
    private JPanel buildTabCabine() {
        JPanel panel = darkPanel(new BorderLayout(12, 12));
        panel.setBorder(new EmptyBorder(16, 16, 16, 16));

        // Form
        JTextField fId        = styledField("es. CAB-001");
        JTextField fCodice    = styledField("es. C-101");
        JTextField fUbicazione = styledField("es. Zona Industriale Nord");

        JPanel form = buildForm(new String[]{"ID Cabina", "Codice", "Ubicazione"},
                                new JTextField[]{fId, fCodice, fUbicazione});

        JButton btnAggiungi = accentButton("+ Aggiungi Cabina", ACCENT);
        btnAggiungi.addActionListener(e -> {
            String id  = fId.getText().trim();
            String cod = fCodice.getText().trim();
            String ubi = fUbicazione.getText().trim();
            if (id.isEmpty() || cod.isEmpty() || ubi.isEmpty()) {
                showError("Compila tutti i campi."); return;
            }
            gestoreCabine.getCabine().add(new Cabina(id, cod, ubi));
            modelCabine.addRow(new Object[]{id, cod, ubi});
            fId.setText(""); fCodice.setText(""); fUbicazione.setText("");
            showSuccess("Cabina aggiunta!");
        });

        JPanel formCard = wrapInCard("Nuova Cabina", form, btnAggiungi);

        // Tabella
        modelCabine = new DefaultTableModel(new String[]{"ID", "Codice", "Ubicazione"}, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        JScrollPane tablePane = buildTable(modelCabine);

        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, formCard, tablePane);
        split.setDividerLocation(340);
        split.setBackground(BG_DARK);
        split.setBorder(null);

        panel.add(split, BorderLayout.CENTER);
        return panel;
    }

    // ══════════════════════════════════════════════════════════════════════
    //  TAB QUADRI
    // ══════════════════════════════════════════════════════════════════════
    private JPanel buildTabQuadri() {
        JPanel panel = darkPanel(new BorderLayout(12, 12));
        panel.setBorder(new EmptyBorder(16, 16, 16, 16));

        JTextField fId        = styledField("es. QE-001");
        JTextField fNome      = styledField("es. Quadro Generale");
        JTextField fTipologia = styledField("es. BT / MT / AT");

        JPanel form = buildForm(new String[]{"ID Quadro", "Nome", "Tipologia"},
                                new JTextField[]{fId, fNome, fTipologia});

        // Combo cabine
        JLabel lblCab = styledLabel("Cabina associata");
        JComboBox<String> comboCabine = styledCombo();
        JPanel comboRow = new JPanel(new BorderLayout(6, 4));
        comboRow.setOpaque(false);
        comboRow.add(lblCab, BorderLayout.NORTH);
        comboRow.add(comboCabine, BorderLayout.CENTER);

        JButton btnAggiungi = accentButton("+ Aggiungi Quadro", ACCENT2);
        btnAggiungi.addActionListener(e -> {
            if (gestoreCabine.getCabine().isEmpty()) {
                showError("Prima inserisci almeno una cabina!"); return;
            }
            refreshCombo(comboCabine, gestoreCabine.getCabine().stream()
                    .map(c -> c.getId() + " – " + c.getCodice())
                    .toArray(String[]::new));

            String id  = fId.getText().trim();
            String nom = fNome.getText().trim();
            String tip = fTipologia.getText().trim();
            if (id.isEmpty() || nom.isEmpty() || tip.isEmpty()) {
                showError("Compila tutti i campi."); return;
            }
            int idx = comboCabine.getSelectedIndex();
            if (idx < 0) { showError("Seleziona una cabina."); return; }
            Cabina cab = gestoreCabine.getCabine().get(idx);
            gestoreQuadri.getQuadri().add(new QuadroElettrico(id, nom, tip, cab));
            modelQuadri.addRow(new Object[]{id, nom, tip, cab.getCodice()});
            fId.setText(""); fNome.setText(""); fTipologia.setText("");
            showSuccess("Quadro aggiunto!");
        });

        // Aggiorna combo all'apertura del tab
        btnAggiungi.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                refreshCombo(comboCabine, gestoreCabine.getCabine().stream()
                        .map(c -> c.getId() + " – " + c.getCodice())
                        .toArray(String[]::new));
            }
        });

        JPanel formFull = new JPanel();
        formFull.setOpaque(false);
        formFull.setLayout(new BoxLayout(formFull, BoxLayout.Y_AXIS));
        formFull.add(form);
        formFull.add(Box.createVerticalStrut(8));
        formFull.add(comboRow);

        JPanel formCard = wrapInCard("Nuovo Quadro Elettrico", formFull, btnAggiungi);

        modelQuadri = new DefaultTableModel(new String[]{"ID", "Nome", "Tipologia", "Cabina"}, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        JScrollPane tablePane = buildTable(modelQuadri);

        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, formCard, tablePane);
        split.setDividerLocation(360);
        split.setBackground(BG_DARK);
        split.setBorder(null);

        panel.add(split, BorderLayout.CENTER);
        return panel;
    }

    // ══════════════════════════════════════════════════════════════════════
    //  TAB TECNICI
    // ══════════════════════════════════════════════════════════════════════
    private JPanel buildTabTecnici() {
        JPanel panel = darkPanel(new BorderLayout(12, 12));
        panel.setBorder(new EmptyBorder(16, 16, 16, 16));

        JTextField fId      = styledField("es. TEC-001");
        JTextField fNome    = styledField("es. Mario");
        JTextField fCognome = styledField("es. Rossi");
        JTextField fReparto = styledField("es. Manutenzione");

        JPanel form = buildForm(
                new String[]{"ID Tecnico", "Nome", "Cognome", "Reparto"},
                new JTextField[]{fId, fNome, fCognome, fReparto});

        JButton btnAggiungi = accentButton("+ Aggiungi Tecnico", WARNING);
        btnAggiungi.addActionListener(e -> {
            String id  = fId.getText().trim();
            String nom = fNome.getText().trim();
            String cog = fCognome.getText().trim();
            String rep = fReparto.getText().trim();
            if (id.isEmpty() || nom.isEmpty() || cog.isEmpty() || rep.isEmpty()) {
                showError("Compila tutti i campi."); return;
            }
            gestoreTecnici.getTecnici().add(new Tecnico(id, nom, cog, rep));
            modelTecnici.addRow(new Object[]{id, nom, cog, rep});
            fId.setText(""); fNome.setText(""); fCognome.setText(""); fReparto.setText("");
            showSuccess("Tecnico aggiunto!");
        });

        JPanel formCard = wrapInCard("Nuovo Tecnico", form, btnAggiungi);

        modelTecnici = new DefaultTableModel(new String[]{"ID", "Nome", "Cognome", "Reparto"}, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        JScrollPane tablePane = buildTable(modelTecnici);

        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, formCard, tablePane);
        split.setDividerLocation(340);
        split.setBackground(BG_DARK);
        split.setBorder(null);

        panel.add(split, BorderLayout.CENTER);
        return panel;
    }

    // ══════════════════════════════════════════════════════════════════════
    //  TAB INTERVENTI
    // ══════════════════════════════════════════════════════════════════════
    private JPanel buildTabInterventi() {
        JPanel panel = darkPanel(new BorderLayout(12, 12));
        panel.setBorder(new EmptyBorder(16, 16, 16, 16));

        // ── Campi testo ────────────────────────────────────────────────
        JTextField fId   = styledField("es. INT-001");
        JTextField fData = styledField("es. 2024-06-15");
        JTextField fEsito = styledField("es. Completato");
        JTextField fNote  = styledField("Note aggiuntive...");

        // ── Combo dinamiche ────────────────────────────────────────────
        JComboBox<String> comboTecnici = styledCombo();
        JComboBox<String> comboCabine  = styledCombo();
        JComboBox<String> comboQuadri  = styledCombo();
        JComboBox<String> comboPriorita = styledCombo();
        comboPriorita.addItem("BASSA");
        comboPriorita.addItem("MEDIA");
        comboPriorita.addItem("ALTA");

        // ── Form sinistra ──────────────────────────────────────────────
        JPanel formLeft = buildForm(
                new String[]{"ID Intervento", "Data (AAAA-MM-GG)", "Esito", "Note"},
                new JTextField[]{fId, fData, fEsito, fNote});

        // ── Form destra ────────────────────────────────────────────────
        JPanel formRight = new JPanel();
        formRight.setOpaque(false);
        formRight.setLayout(new BoxLayout(formRight, BoxLayout.Y_AXIS));

        for (String[] pair : new String[][]{
                {"Tecnico", null}, {"Cabina", null}, {"Quadro", null}, {"Priorità", null}
        }) {
            // solo etichette; i combo li aggiungo sotto
        }

        addLabeledCombo(formRight, "Tecnico",   comboTecnici);
        addLabeledCombo(formRight, "Cabina",    comboCabine);
        addLabeledCombo(formRight, "Quadro",    comboQuadri);
        addLabeledCombo(formRight, "Priorità",  comboPriorita);

        JPanel formBoth = new JPanel(new GridLayout(1, 2, 12, 0));
        formBoth.setOpaque(false);
        formBoth.add(formLeft);
        formBoth.add(formRight);

        // ── Bottone aggiungi ───────────────────────────────────────────
        JButton btnAggiungi = accentButton("+ Registra Intervento", ACCENT);
        btnAggiungi.addActionListener(e -> {
            // Aggiorna i combo al momento del click
            refreshCombo(comboTecnici, gestoreTecnici.getTecnici().stream()
                    .map(t -> t.getId() + " – " + t.getNome() + " " + t.getCognome())
                    .toArray(String[]::new));
            refreshCombo(comboCabine, gestoreCabine.getCabine().stream()
                    .map(c -> c.getId() + " – " + c.getCodice())
                    .toArray(String[]::new));
            refreshCombo(comboQuadri, gestoreQuadri.getQuadri().stream()
                    .map(q -> q.getId() + " – " + q.getNome())
                    .toArray(String[]::new));

            if (gestoreTecnici.getTecnici().isEmpty() ||
                gestoreCabine.getCabine().isEmpty() ||
                gestoreQuadri.getQuadri().isEmpty()) {
                showError("Assicurati di avere almeno un tecnico, una cabina e un quadro!"); return;
            }

            String id   = fId.getText().trim();
            String data = fData.getText().trim();
            String esito = fEsito.getText().trim();
            String note  = fNote.getText().trim();

            if (id.isEmpty() || data.isEmpty() || esito.isEmpty()) {
                showError("Compila almeno ID, Data ed Esito."); return;
            }

            int iT = comboTecnici.getSelectedIndex();
            int iC = comboCabine.getSelectedIndex();
            int iQ = comboQuadri.getSelectedIndex();

            if (iT < 0 || iC < 0 || iQ < 0) {
                showError("Seleziona tecnico, cabina e quadro."); return;
            }

            Tecnico       tec  = gestoreTecnici.getTecnici().get(iT);
            Cabina        cab  = gestoreCabine.getCabine().get(iC);
            QuadroElettrico qua = gestoreQuadri.getQuadri().get(iQ);
            String        pri  = (String) comboPriorita.getSelectedItem();

            Intervento inv = new Intervento(id, data, tec, cab, qua, esito, note, pri);
            gestoreInterventi.getInterventi().add(inv);

            // Colore priorità
            String badge = pri.equals("ALTA") ? "🔴" : pri.equals("MEDIA") ? "🟡" : "🟢";
            modelInterventi.addRow(new Object[]{
                id, data,
                tec.getNome() + " " + tec.getCognome(),
                cab.getCodice(),
                qua.getNome(),
                esito,
                badge + " " + pri,
                note
            });

            fId.setText(""); fData.setText(""); fEsito.setText(""); fNote.setText("");
            showSuccess("Intervento registrato!");
        });

        // Aggiorna combo al passaggio del mouse sul bottone
        btnAggiungi.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                refreshCombo(comboTecnici, gestoreTecnici.getTecnici().stream()
                        .map(t -> t.getId() + " – " + t.getNome() + " " + t.getCognome())
                        .toArray(String[]::new));
                refreshCombo(comboCabine, gestoreCabine.getCabine().stream()
                        .map(c -> c.getId() + " – " + c.getCodice())
                        .toArray(String[]::new));
                refreshCombo(comboQuadri, gestoreQuadri.getQuadri().stream()
                        .map(q -> q.getId() + " – " + q.getNome())
                        .toArray(String[]::new));
            }
        });

        // ── Barra ricerca ──────────────────────────────────────────────
        JPanel searchBar = darkPanel(new FlowLayout(FlowLayout.LEFT, 8, 4));
        JTextField fSearch = styledField("Cerca per nome tecnico...");
        fSearch.setPreferredSize(new Dimension(240, 32));
        JButton btnSearch = accentButton("🔍 Cerca", TEXT_SUB);
        btnSearch.addActionListener(e -> {
            String q = fSearch.getText().trim().toLowerCase();
            for (int r = 0; r < modelInterventi.getRowCount(); r++) {
                String tec = modelInterventi.getValueAt(r, 2).toString().toLowerCase();
                // evidenzia riga (selezione)
                // In Swing non c'è un filtro nativo semplice senza RowSorter
            }
            // Ricerca semplice: mostra solo le righe che matchano
            ArrayList<Object[]> all = new ArrayList<>();
            for (int r = 0; r < modelInterventi.getRowCount(); r++) {
                all.add(new Object[]{
                    modelInterventi.getValueAt(r,0), modelInterventi.getValueAt(r,1),
                    modelInterventi.getValueAt(r,2), modelInterventi.getValueAt(r,3),
                    modelInterventi.getValueAt(r,4), modelInterventi.getValueAt(r,5),
                    modelInterventi.getValueAt(r,6), modelInterventi.getValueAt(r,7)
                });
            }
            modelInterventi.setRowCount(0);
            for (Object[] row : all) {
                if (q.isEmpty() || row[2].toString().toLowerCase().contains(q)) {
                    modelInterventi.addRow(row);
                }
            }
        });
        JButton btnReset = accentButton("✕ Reset", DANGER);
        btnReset.addActionListener(e -> {
            modelInterventi.setRowCount(0);
            gestoreInterventi.getInterventi().forEach(inv -> {
                String p = inv.getPriorita();
                String badge = p.equals("ALTA") ? "🔴" : p.equals("MEDIA") ? "🟡" : "🟢";
                modelInterventi.addRow(new Object[]{
                    inv.getId(),
                    inv.getData(),
                    inv.getTecnico().getNome() + " " + inv.getTecnico().getCognome(),
                    inv.getCabina().getCodice(),
                    inv.getQuadro().getNome(),
                    inv.getEsito(),
                    badge + " " + p,
                    inv.getNote()
                });
            });
            fSearch.setText("");
        });
        searchBar.add(new JLabel("  "));
        searchBar.add(fSearch);
        searchBar.add(btnSearch);
        searchBar.add(btnReset);

        // ── Tabella interventi ─────────────────────────────────────────
        modelInterventi = new DefaultTableModel(
                new String[]{"ID", "Data", "Tecnico", "Cabina", "Quadro", "Esito", "Priorità", "Note"}, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        JScrollPane tablePane = buildTable(modelInterventi);

        JPanel formCard = wrapInCard("Nuovo Intervento", formBoth, btnAggiungi);

        JSplitPane split = new JSplitPane(JSplitPane.VERTICAL_SPLIT, formCard, tablePane);
        split.setDividerLocation(300);
        split.setBackground(BG_DARK);
        split.setBorder(null);

        panel.add(searchBar, BorderLayout.NORTH);
        panel.add(split,     BorderLayout.CENTER);
        return panel;
    }

    // ══════════════════════════════════════════════════════════════════════
    //  HELPER UI
    // ══════════════════════════════════════════════════════════════════════

    private JPanel darkPanel(LayoutManager layout) {
        JPanel p = new JPanel(layout);
        p.setBackground(BG_DARK);
        return p;
    }

    private JLabel styledLabel(String text) {
        JLabel l = new JLabel(text);
        l.setFont(new Font("Monospaced", Font.BOLD, 12));
        l.setForeground(TEXT_SUB);
        return l;
    }

    private JTextField styledField(String placeholder) {
        JTextField f = new JTextField() {
            @Override protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (getText().isEmpty() && !isFocusOwner()) {
                    g.setColor(new Color(100, 130, 170));
                    g.setFont(new Font("Monospaced", Font.ITALIC, 12));
                    g.drawString(placeholder, 6, 18);
                }
            }
        };
        f.setBackground(BG_DARK);
        f.setForeground(TEXT_MAIN);
        f.setCaretColor(ACCENT);
        f.setFont(new Font("Monospaced", Font.PLAIN, 13));
        f.setBorder(BorderFactory.createCompoundBorder(
            new MatteBorder(0, 0, 2, 0, ACCENT),
            new EmptyBorder(4, 6, 4, 6)
        ));
        f.setPreferredSize(new Dimension(0, 34));
        return f;
    }

    private JComboBox<String> styledCombo() {
        JComboBox<String> c = new JComboBox<>();
        c.setBackground(BG_CARD);
        c.setForeground(TEXT_MAIN);
        c.setFont(new Font("Monospaced", Font.PLAIN, 12));
        c.setBorder(new MatteBorder(0, 0, 2, 0, BORDER_COL));
        return c;
    }

    private void refreshCombo(JComboBox<String> combo, String[] items) {
        int prev = combo.getSelectedIndex();
        combo.removeAllItems();
        for (String s : items) combo.addItem(s);
        if (prev >= 0 && prev < combo.getItemCount()) combo.setSelectedIndex(prev);
    }

    private JButton accentButton(String text, Color color) {
        JButton b = new JButton(text) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                if (getModel().isPressed()) {
                    g2.setColor(color.darker());
                } else if (getModel().isRollover()) {
                    g2.setColor(color.brighter());
                } else {
                    g2.setColor(color);
                }
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
                g2.setColor(BG_DARK);
                g2.setFont(getFont());
                FontMetrics fm = g2.getFontMetrics();
                int x = (getWidth() - fm.stringWidth(getText())) / 2;
                int y = (getHeight() + fm.getAscent() - fm.getDescent()) / 2;
                g2.drawString(getText(), x, y);
                g2.dispose();
            }
        };
        b.setFont(new Font("Monospaced", Font.BOLD, 13));
        b.setForeground(BG_DARK);
        b.setBorderPainted(false);
        b.setContentAreaFilled(false);
        b.setFocusPainted(false);
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        b.setPreferredSize(new Dimension(220, 38));
        return b;
    }

    private JPanel buildForm(String[] labels, JTextField[] fields) {
        JPanel p = new JPanel(new GridLayout(labels.length, 1, 0, 8));
        p.setOpaque(false);
        for (int i = 0; i < labels.length; i++) {
            JPanel row = new JPanel(new BorderLayout(0, 4));
            row.setOpaque(false);
            row.add(styledLabel(labels[i]), BorderLayout.NORTH);
            row.add(fields[i],              BorderLayout.CENTER);
            p.add(row);
        }
        return p;
    }

    private void addLabeledCombo(JPanel parent, String label, JComboBox<String> combo) {
        JPanel row = new JPanel(new BorderLayout(0, 4));
        row.setOpaque(false);
        row.setBorder(new EmptyBorder(0, 0, 8, 0));
        row.add(styledLabel(label), BorderLayout.NORTH);
        row.add(combo,              BorderLayout.CENTER);
        parent.add(row);
    }

    private JPanel wrapInCard(String title, JComponent form, JButton btn) {
        JPanel card = new JPanel(new BorderLayout(0, 12));
        card.setBackground(BG_PANEL);
        card.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(BORDER_COL, 1, true),
            new EmptyBorder(16, 16, 16, 16)
        ));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Monospaced", Font.BOLD, 15));
        titleLabel.setForeground(ACCENT);
        titleLabel.setBorder(new MatteBorder(0, 0, 1, 0, BORDER_COL));

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        btnPanel.setOpaque(false);
        btnPanel.add(btn);

        card.add(titleLabel, BorderLayout.NORTH);
        card.add(form,       BorderLayout.CENTER);
        card.add(btnPanel,   BorderLayout.SOUTH);
        return card;
    }

    private JScrollPane buildTable(DefaultTableModel model) {
        JTable table = new JTable(model);
        table.setBackground(BG_CARD);
        table.setForeground(TEXT_MAIN);
        table.setFont(new Font("Monospaced", Font.PLAIN, 12));
        table.setRowHeight(28);
        table.setGridColor(BORDER_COL);
        table.setSelectionBackground(new Color(0, 100, 160));
        table.setSelectionForeground(Color.WHITE);
        table.setShowHorizontalLines(true);
        table.setShowVerticalLines(false);
        table.getTableHeader().setBackground(BG_PANEL);
        table.getTableHeader().setForeground(ACCENT);
        table.getTableHeader().setFont(new Font("Monospaced", Font.BOLD, 12));
        table.getTableHeader().setBorder(new MatteBorder(0, 0, 2, 0, ACCENT));

        // Righe alternate
        table.setDefaultRenderer(Object.class, new javax.swing.table.DefaultTableCellRenderer() {
            @Override public Component getTableCellRendererComponent(
                    JTable t, Object v, boolean sel, boolean foc, int row, int col) {
                super.getTableCellRendererComponent(t, v, sel, foc, row, col);
                setBackground(sel ? new Color(0, 100, 160)
                                  : (row % 2 == 0 ? BG_CARD : BG_PANEL));
                setForeground(TEXT_MAIN);
                setBorder(new EmptyBorder(0, 8, 0, 8));
                return this;
            }
        });

        JScrollPane sp = new JScrollPane(table);
        sp.setBackground(BG_DARK);
        sp.getViewport().setBackground(BG_CARD);
        sp.setBorder(new LineBorder(BORDER_COL, 1));
        return sp;
    }

    // ══════════════════════════════════════════════════════════════════════
    //  DIALOG FEEDBACK
    // ══════════════════════════════════════════════════════════════════════
    private void showSuccess(String msg) {
        JOptionPane.showMessageDialog(this, msg, "✔ Successo", JOptionPane.INFORMATION_MESSAGE);
    }

    private void showError(String msg) {
        JOptionPane.showMessageDialog(this, msg, "✖ Errore", JOptionPane.ERROR_MESSAGE);
    }

    // ══════════════════════════════════════════════════════════════════════
    //  MAIN
    // ══════════════════════════════════════════════════════════════════════
    public static void main(String[] args) {
        // Look & Feel di sistema per i componenti standard
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception ignored) {}

        SwingUtilities.invokeLater(MainGUI::new);
    }
}
