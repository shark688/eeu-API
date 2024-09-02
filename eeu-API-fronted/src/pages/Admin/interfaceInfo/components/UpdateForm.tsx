import {ProColumns, ProFormInstance, ProTable,} from '@ant-design/pro-components';
import '@umijs/max';
import {Modal} from 'antd';
import React, {useEffect, useRef} from 'react';

export type CreateFormProps = {
  values: API.InterfaceInfo;
  columns: ProColumns<API.InterfaceInfo>[];
  onCancel: () => void;
  onSubmit: (values: API.InterfaceInfo) => Promise<void>;
  visible: boolean;
};

const UpdateForm: React.FC<CreateFormProps> = (props) => {
  const {columns,visible,onSubmit,onCancel,values} = props

  const formRef = useRef<ProFormInstance>();

  useEffect(()=>{
    if(formRef)
    {
      formRef.current?.setFieldsValue(values)
    }
  },[values])

  return<Modal visible={visible} footer={null} onCancel={()=>onCancel?.()}>
    <ProTable
      formRef={formRef}
      type={"form"} columns={columns} onSubmit={
      async (value) => {
        onSubmit?.(value);
      }}/>
    </Modal>;
};
export default UpdateForm;
