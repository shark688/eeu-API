import {PageContainer} from '@ant-design/pro-components';
import React, {useEffect, useState} from 'react';
import {Button, Card, Descriptions, Divider, Form, message, Spin} from "antd";
import {getInterfaceInfoVoByIdUsingGet, interfaceInvokeUsingPost} from "@/services/eeu/interfaceInfoController";
import {useParams} from "react-router";
import TextArea from "antd/lib/input/TextArea";

/**
 * 主页
 * @constructor
 */
const Index: React.FC = () => {
  const [loading, setLoading] = useState(false);
  const [responseLoading, setResponseLoading] = useState(false);
  const [data,  setData] = useState<API.InterfaceInfo>();
  const [responseData,  setResponseData] = useState<any>();
  const param = useParams();

  function formatDate(dateString) {
    const date = new Date(dateString);
    // 使用Intl.DateTimeFormat进行国际化日期格式化
    const formatter = new Intl.DateTimeFormat('zh-CN', {
      year: 'numeric',
      month: '2-digit',
      day: '2-digit',
    });
    return formatter.format(date); // 手动添加'日'字
  }



  const loadData = async () => {
    setLoading(true);
    if(!param.id)
    {
       message.error("该接口文档不存在!");
       return;
    }
    try{
      const  res = await getInterfaceInfoVoByIdUsingGet({
        id: Number(param.id)
      });
      setData(res.data)
    }
    catch (error: any)
    {
      message.error("请求失败!"+error.message);
    }
    setLoading(false);
  }

  useEffect(()=>{
    loadData()
  },[])

  const onFinish = async (values: any) => {
    setResponseLoading(true);
    if(!param.id)
    {
      message.error("该接口文档不存在!");
      return;
    }
    try{
      const res = await interfaceInvokeUsingPost({
        id: param.id,
        ...values
      })
      setResponseData(res.data)
      message.success("请求成功!")
    }
    catch (error: any)
    {
      message.error("请求失败!"+error.message);
    }
    setResponseLoading(false);
  };

  return (
    <PageContainer title={"查看接口文档"}>
      <Card>
        {
          data ? <Descriptions title={data.name} column={1} extra={<Button> 调用 </Button>}>
            <Descriptions.Item label="接口状态">{data.status === 0 ? "未上线" : "正常"}</Descriptions.Item>
            <Descriptions.Item label="描述">{data.description}</Descriptions.Item>
            <Descriptions.Item label="请求地址">{data.url}</Descriptions.Item>
            <Descriptions.Item label="请求参数">{data.requestParams}</Descriptions.Item>
            <Descriptions.Item label="请求方法">{data.method}</Descriptions.Item>
            <Descriptions.Item label="请求头">{data.requestHeader}</Descriptions.Item>
            <Descriptions.Item label="响应头">{data.responseHeader}</Descriptions.Item>
            <Descriptions.Item label="创建时间">{formatDate(data.createTime)}</Descriptions.Item>
            <Descriptions.Item label="更新时间">{formatDate(data.updateTime)}</Descriptions.Item>
          </Descriptions> : <>接口不存在</>
        }
      </Card>
      <Divider></Divider>
      <Card title={"在线测试"}>
        <Form
          name="invoke"
          onFinish={onFinish}
          autoComplete="off"
          layout={"vertical"}
        >
          <Form.Item
            label="请求参数"
            name="userRequestParams"
          >
            <TextArea />
          </Form.Item>
          <Form.Item wrapperCol={{ offset: 8, span: 16 }}>
            <Button type="primary" htmlType="submit">
              调用
            </Button>
          </Form.Item>
        </Form>
      </Card>
      <Divider></Divider>
      <Card loading={responseLoading} title={"调用结果"}>
        {responseData}
      </Card>
    </PageContainer>
  );
};

export default Index;
