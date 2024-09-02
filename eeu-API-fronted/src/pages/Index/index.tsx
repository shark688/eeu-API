import {PageContainer} from '@ant-design/pro-components';
import React, {useEffect, useState} from 'react';
import {List, message} from "antd";
import {listInterfaceInfoByPageUsingPost} from "@/services/eeu/interfaceInfoController";

/**
 * 主页
 * @constructor
 */
const Index: React.FC = () => {
  const [loading, setLoading] = useState(false);
  const [list, setList] = useState<API.InterfaceInfo[]>([]);
  const [total,  setTotal] = useState<number>(0);

  const loadData = async (current: number = 1, pageSize: number = 10) => {
    setLoading(true);
    try{
      const  res = await listInterfaceInfoByPageUsingPost({
          current,pageSize
      });
      setList(res?.data?.records ?? []);
      setTotal(res?.data?.total ?? 0);
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

  return (
    <PageContainer title={"eeu在线接口平台"}>
      <List
        className="my-interface-list"
        loading={loading}
        itemLayout="horizontal"
        dataSource={list}
        renderItem={item => {
          const apiLink = `/interfaceInfo/${item.id}`;
          return (
            <List.Item
              actions={[<a key = {item.id} href = {apiLink}>查看</a>]}
            >
              <List.Item.Meta
                title={<a href={apiLink}>{item.name}</a>}
                description={item.description}
              />
            </List.Item>
          )
        }}
        pagination={
          {
            showTotal(total:number) {
              return '总共: '+total+ ' 个接口';
            },
            pageSize: 10,
            total: total,
            onChange(page,pageSize){
                loadData(page,pageSize)
            }
          }
        }
      />
    </PageContainer>
  );
};

export default Index;
