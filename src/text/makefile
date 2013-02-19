
all: text.pdf

text.pdf: text.fo
	fop -fo install.fo -pdf install.pdf

text.fo: install.xml
	xsltproc -o install.fo \
                      --stringparam use.extensions 0 \
                      --stringparam fop1.extensions 1 \
                      --stringparam draft.mode no \
                      --stringparam hyphenate false \
                      --stringparam l10n.gentext.default.language de \
                      --stringparam admon.graphics 1 \
                      --stringparam admon.graphics.extension .svg \
                      /usr/share/xml/docbook/stylesheet/nwalsh/fo/docbook.xsl \
                      install.xml
