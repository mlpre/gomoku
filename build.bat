chcp 65001
jpackage -n "五子棋" --runtime-image ./target/image -m gomoku/ml.minli.Gomoku -d ./target --win-dir-chooser --win-menu --win-shortcut
