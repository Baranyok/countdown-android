PROJ_NAME = countdown

PACK_DIR = pack
SRC_ZIP = Countdown.zip
TARGET_ZIP = xkalaz00_src.zip

FILE_LIST = readme.txt ATTRIBUTION.md *.gradle gradle* app

zip: $(PACK_DIR)
	cd $</$(PROJ_NAME) && zip -r $(TARGET_ZIP) $(FILE_LIST)
	mv $</$(PROJ_NAME)/$(TARGET_ZIP) ./

$(PACK_DIR): $(SRC_ZIP)
	unzip -u $< -d $@
