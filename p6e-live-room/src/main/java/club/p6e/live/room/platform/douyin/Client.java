package club.p6e.live.room.platform.douyin;

/**
 * @author lidashuang
 * @version 1.0
 */
public class Client {

    public interface Intensifier {
        /**
         * 增强原本的客户端对象
         * @param client 客户端对象
         * @return 客户端对象
         */
        public Client enhance(Client client);
    }

}
