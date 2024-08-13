import {ProColumns, ProTable,} from '@ant-design/pro-components';
import '@umijs/max';
import {Modal} from 'antd';
import React from 'react';

export type CreateFormProps = {
  columns: ProColumns<API.InterfaceInfo>[];
  onCancel: () => void;
  onSubmit: (values: API.InterfaceInfo) => Promise<void>;
  visible: boolean;
};

const CreateForm: React.FC<CreateFormProps> = (props) => {
  const {columns,visible,onSubmit,onCancel} = props

  return<Modal visible={visible} footer={null} onCancel={()=>onCancel?.()}>
      <ProTable type={"form"} columns={columns} onSubmit={
        async (value) => {
         onSubmit?.(value);
      }}/>
    </Modal>;
};
export default CreateForm;
