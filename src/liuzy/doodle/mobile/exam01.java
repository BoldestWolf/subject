package liuzy.doodle.mobile;

/**
 * 验证合法的IPv4地址
 * 
 * @author liuzy
 *
 */
public class exam01 {

	/**
	 * 合法的IPv4地址校验器
	 * 
	 * @param address
	 * 		ip地址
	 * @return
	 * 		true or false
	 */
	static boolean validate(String address) {
		String[] splitAddress = address.split("\\.");
		if (splitAddress.length != 4) {
			return false;
		}
		for (int i = 0; i < splitAddress.length; i++) {
			char firstChar = splitAddress[i].charAt(0);
			if (splitAddress[i].length() > 1 && new String("0").equals(Character.toString(firstChar))) {
				return false;
			} else {
				Integer intValue = Integer.parseInt(splitAddress[i]);
				String binaryString = Integer.toBinaryString(intValue);
				if (binaryString.length() > 8) {
					return false;
				}
			}
		}
		return true;
	}

	public static void main(String[] args) {
		System.out.println(validate(new String("1.2.3.4")));
		System.out.println(validate(new String("1.2.3.4.5")));
		System.out.println(validate(new String("123.456.78.90")));
		System.out.println(validate(new String("255.255.255.255")));
		System.out.println(validate(new String("0.0.0.0")));
		System.out.println(validate(new String("123.045.067.089")));
	}
}
