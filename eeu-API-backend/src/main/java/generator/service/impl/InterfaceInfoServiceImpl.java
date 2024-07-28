package generator.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import generator.domain.InterfaceInfo;
import generator.mapper.InterfaceInfoMapper;
import generator.service.InterfaceInfoService;
import org.springframework.stereotype.Service;

/**
* @author 张瑞东
* @description 针对表【interface_info(接口信息)】的数据库操作Service实现
* @createDate 2024-07-27 19:57:59
*/
@Service
public class InterfaceInfoServiceImpl extends ServiceImpl<InterfaceInfoMapper, InterfaceInfo>
    implements InterfaceInfoService{

}




