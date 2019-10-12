# -*- coding: utf-8 -*-

import sys

from PyQt5.QtWidgets import QApplication, QWidget,QLineEdit, QPushButton,QFileDialog,QCheckBox,\
     QLabel,QRadioButton,QMessageBox,QFrame,QComboBox, QFontDialog, QColorDialog
#from PyQt5.QtGui import
from PyQt5.QtCore import *
from PyQt5.QtGui import QFont,QColor,QPalette
from PyQt5 import Qt

class PyQtVariousParts(QWidget):

    def __init__(self):
        super(PyQtVariousParts,self).__init__()
        self.frameCreate()

    def frameCreate(self):

        self.setWindowTitle("部品実験")
        self.resize(500,230)

        #各パーツ作成
        #1列目
        self.checkbox = QCheckBox("YES / NO",self)
        self.checkon_button = QPushButton("チェック操作",self)

        #2列目
        self.radio_button = QRadioButton("選択式①",self)
        self.radio_button2 = QRadioButton("選択式②",self)
        self.radio_onoffbutton = QPushButton("選択操作",self)
        #3列目
        self.combbox = QComboBox(self)
        self.dialog_button = QPushButton("ダイアログ操作",self)
        self.textarea = QLineEdit(self)
        self.sepline = QFrame()  #仕切線

        #パーツを配置
        self.checkbox.setGeometry(20,0,200,75)
        self.checkon_button.setGeometry(200,23,75,25)
        self.radio_button.setGeometry(20,70,75,25)
        self.radio_button2.setGeometry(130,70,75,25)
        self.radio_onoffbutton.setGeometry(240,70,75,25)
        self.combbox.setGeometry(20,120,150,25)
        self.dialog_button.setGeometry(20,170,100,25)
        self.textarea.setGeometry(150,170,300,25)

        #線はレイアウトに配置
        self.sepline.setFrameStyle( QFrame.HLine | QFrame.Plain )
        self.sepline.setGeometry(100,200,100,100)

        #コンボボックスの中身を配置
        self.setComboBoxContent()
        self.textarea.setPlaceholderText("板村 陽花ちゃん")

        #イベントハンドラ
        self.checkbox.stateChanged.connect(self.CheckBoxOperate)
        self.checkon_button.clicked.connect(self.CheckBoxOperate2)
        self.radio_button.toggled.connect(self.radiochange)
        self.radio_onoffbutton.clicked.connect(self.radiobuttonchoice)
        self.combbox.activated.connect(self.comboact)
        self.dialog_button.clicked.connect(self.dialogopen)
        #self.dirselection_but.clicked.connect(self.dirselect)
        #self.filecreate_but.clicked.connect(self.filecreate)

        self.show()


    def CheckBoxOperate(self):

        if self.checkbox.isChecked():
            print("checkd")
        else:
            print("unchecked")

    def CheckBoxOperate2(self):

        if self.checkbox.isChecked():
            self.checkbox.setCheckState(0)
        else:
            self.checkbox.setCheckState(2)

    def radiochange(self):

        print(self.radio_button.isChecked())

    def radiobuttonchoice(self):

        if self.radio_button.isChecked():
            self.radio_button.setChecked(False)
            self.radio_button2.setChecked(True)
        elif self.radio_button2.isChecked():
            self.radio_button.setChecked(True)
            self.radio_button2.setChecked(False)
        else:

            msgBox = QMessageBox.critical(self, "警告", "チェックしろって",QMessageBox.No | QMessageBox.Yes | QMessageBox.Save,QMessageBox.Yes)
            if msgBox == QMessageBox.Yes:
                print("さっさと選べよ")
            elif msgBox == QMessageBox.Save:
                print("はんこうするなって")


            print("選べ")

    def setComboBoxContent(self):
        self.combbox.setInsertPolicy(QComboBox.InsertAtTop)
        import codecs
        contents = codecs.open("C:\\Users\\enoch\\Desktop\\horse.txt" ,"r", "shift_jis")
        for content in contents:
            horse = content.replace("\r\n","").split('\t')
            self.combbox.addItem(horse[0],horse[1])


    def comboact(self):
        print(self.combbox.itemData(self.combbox.currentIndex()))

    #ウィジェットの×ボタン押下時
    def closeEvent(self,event):

        #確認ダイアログ
        confirm = QMessageBox.question(self, "閉じちゃうぞ", "OKですか?",QMessageBox.No | QMessageBox.Yes ,QMessageBox.No)
        if confirm == QMessageBox.Yes:
            event.accept()
        else:
            event.ignore()


    def dialogopen(self):
        #現在のフォントを取得


        #フォント選択ダイアログ
        fontset = QFontDialog.getFont(QFont('MS ゴシック',16), self, caption="選択して")
        #print(fontset[0])
        self.textarea.setFont(fontset[0])   #フォント設定
        print(fontset[1])                   #OKボタン押下⇒ True
        import time
        time.sleep(1)

        colorset = QColorDialog.getColor(initial=QColor(204,0,0,255), parent=self, title="選択して")
        print(colorset)
        color=QPalette()
        color.setColor(10, colorset)
        self.setPalette(color)



def main():
    app = QApplication(sys.argv)
    frame = PyQtVariousParts()
    sys.exit(app.exec_())

if __name__ == '__main__':
    main()
    print("終了")