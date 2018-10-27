#!/bin/bash
#�˰汾����3��dimen���� sw360dp Ϊ��׼��
############################################
#	dos2unix auto_res_tool.sh              #
#	change the format dos to unix command  #	
############################################

DIMEN_VALUE=("360" "320" "411" "375" "480" "540")

#dp������
DIMEN_ATTRIBUTE_NAME="xdp"
#���ɵ�dimen�ĸ���
DIMEN_NUM=1000
#����
DIMEN_STEP=0.5

#sp������
SP_ATTRIBUTE_NAME="xsp"
#sp����
SP_NUM=40
#����
SP_STEP=1

#exit
function fun_exit_shell() {
	echo "exit"
	exit	
}

# create hint
# $1 = 160 , 320 ...; $2 = file_name like dimen.xml etc.
function fuc_create_hint() {
	for value in ${DIMEN_VALUE[@]}
	do  
		if [ $1 == $value ]
		then
			hint="\n<!-- the auto generate dimen values with ${value}dp minimum width -->\n"
			echo -e $hint
			echo -e $hint >> $2
		fi
	done  
}

function fun_create_values_dir() {
	#check dir swXXX is exist, if not, create it!
	for value in ${DIMEN_VALUE[@]}
	do  
		# if dir don't equal value and value-swXXXdp don't exist, then create this dir
		dir_name="values-sw${value}dp"
		if [ ! -d $dir_name ]
		then
			mkdir $dir_name
			echo "create dir ${dir_name} success."
		fi
	done
	
	echo "all value dir have been created success!"
}
#--------------------------------------- the shell start ---------------------------------------

# check current dir
if [ ! -d "src/main/res" ]
then
	echo "please execute this shell in the main module root dir!"
	fun_exit_shell
fi

echo "read dir success!"

cd src/main/res

if [ ! -d "values" ]
then
	fun_exit_shell
fi

echo "there have dir values."

#---------------------------------- in res dir ----------------------------------
#��鲢����Ŀ¼
fun_create_values_dir

#the base base-dpi dimen be created -- start --
echo "the base ${DIMEN_VALUE[0]}dpi dimen be created -- start --"
cd values-sw${DIMEN_VALUE[0]}dp

#check the dimen.xml file is exist
if [ ! -f "dimens.xml" ]
then
	echo "create new file dimens.xml in values."
	touch "dimens.xml"
	echo -e "<resources xmlns:tools=\"http://schemas.android.com/tools\">" >> dimens.xml
	fuc_create_hint ${DIMEN_VALUE[0]} 'dimens.xml'
else
	#have file, and delete the end </resources>
	sed -i "/<\/resources>/d" dimens.xml
	fuc_create_hint ${DIMEN_VALUE[0]} 'dimens.xml'
fi

#ѭ������ dp
echo "create dp in values-sw360dp start"
for ((i=0;i<${DIMEN_NUM};i++))
do
	name=`echo "scale=2; ${DIMEN_STEP}*${i} " | bc -l`
	if [ ${name:0:1} == "." ]
	then
		name="0"$name
	fi
	echo -e "	<dimen name=\"${DIMEN_ATTRIBUTE_NAME}_${name}\">${name}dp</dimen>" >> dimens.xml
done
echo "create dp in values-sw360dp end"

echo -e "\nthe sp res" >> dimens.xml

#ѭ������sp
echo "create sp in values-sw360dp start"
for ((i=0;i<${SP_NUM};i++))
do
	name=`echo "scale=2; ${SP_STEP}*${i} " | bc -l`
	if [ ${name:0:1} == "." ]
	then
		name="0"$name
	fi
	echo -e "	<dimen name=\"${SP_ATTRIBUTE_NAME}_${name}\">${name}sp</dimen>" >> dimens.xml
done
echo "create sp in values-sw360dp end"

echo -e "</resources>" >> dimens.xml
cd -
echo "the base sw360dp dimen be created -- end --"
#the base 360dpi dimen be created -- end --

#the other dir be created -- start --
for ((index=1;index<${#DIMEN_VALUE[@]};index++))
do
	SCALE=`echo "scale=2; ${DIMEN_VALUE[$index]}/360 " | bc -l`
	echo "the SCALE is "$SCALE
	cd values-sw${DIMEN_VALUE[$index]}dp
	#check the dimen.xml file is exist
	if [ ! -f "dimens.xml" ]
	then
		echo "create new file dimens.xml in values-sw${DIMEN_VALUE[$index]}dp."
		touch "dimens.xml"
		echo -e "<resources xmlns:tools=\"http://schemas.android.com/tools\">" >> dimens.xml
		fuc_create_hint ${DIMEN_VALUE[$index]} 'dimens.xml'
	else
		#have file, and delete the end </resources>
		sed -i "/<\/resources>/d" dimens.xml
		fuc_create_hint ${DIMEN_VALUE[$index]} 'dimens.xml'
	fi

	#ѭ������ dp
	echo "create dp in values-sw${DIMEN_VALUE[$index]}dp start"
	for ((i=0;i<${DIMEN_NUM};i++))
	do
		#caculate the res dp
		name=`echo "scale=2; ${DIMEN_STEP}*${i} " | bc -l`
		res=`echo "scale=2; ${DIMEN_STEP}*${SCALE}*${i} " | bc -l`
		if [ ${name:0:1} == "." ]
		then
			name="0"$name
		fi
		if [ ${res:0:1} == "." ]
		then
			res="0"$res
		fi
		echo -e "	<dimen name=\"${DIMEN_ATTRIBUTE_NAME}_${name}\">${res}dp</dimen>" >> dimens.xml
	done
	echo "create dp in values-sw${DIMEN_VALUE[$index]}dp end"
	
	echo -e "\nthe sp res" >> dimens.xml
	
	#ѭ������ sp
	echo "create sp in values-sw${DIMEN_VALUE[$index]}dp start"
	for ((i=0;i<${SP_NUM};i++))
	do
		#caculate the res sp
		name=`echo "scale=2; ${SP_STEP}*${i} " | bc -l`
		res=`echo "scale=2; ${SP_STEP}*${SCALE}*${i} " | bc -l`
		if [ ${name:0:1} == "." ]
		then
			name="0"$name
		fi
		if [ ${res:0:1} == "." ]
		then
			res="0"$res
		fi
		echo -e "	<dimen name=\"${SP_ATTRIBUTE_NAME}_${name}\">${res}sp</dimen>" >> dimens.xml
	done
	echo "create sp in values-sw${DIMEN_VALUE[$index]}dp end"
		
	echo -e "</resources>" >> dimens.xml	
	cd -
done
#the other dir be created -- end --

#echo "scale=2; 1.55 * 1.55 " | bc -l
