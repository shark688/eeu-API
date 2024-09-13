// @ts-ignore
/* eslint-disable */
import { request } from '@umijs/max';

/** listTopInvokeInterfaceInfo GET /api/analysis/interface/top/invoke */
export async function listTopInvokeInterfaceInfoUsingGet(options?: { [key: string]: any }) {
  return request<API.BaseResponseListInterfaceInfoAnalysisVO_>(
    '/api/analysis/interface/top/invoke',
    {
      method: 'GET',
      ...(options || {}),
    },
  );
}
