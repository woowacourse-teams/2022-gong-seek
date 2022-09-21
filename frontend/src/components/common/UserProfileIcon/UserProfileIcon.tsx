import { useRecoilState } from 'recoil';

import gongseek from '@/assets/gongseek.png';
import Dropdown from '@/components/common/Dropdown/Dropdown';
import * as S from '@/components/common/UserProfileIcon/UserProfileIcon.styles';
import useGetUserInfo from '@/hooks/user/useGetUserInfo';
import { dropdownState } from '@/store/dropdownState';

//TODO: 도메인 별로 쪼개자!, common은 공통적으로 사용되는 도메인에서! ex) snackBar
const UserProfileIcon = () => {
	const { data, isLoading, isSuccess } = useGetUserInfo();
	const [dropdown, setDropdown] = useRecoilState(dropdownState);

	return (
		<S.Container>
			{/*TODO: isLoading, isSuccess 처리 통일하자, isSuccess 쓸필요 없을듯 */}
			{isLoading && <S.UserProfile src={gongseek} />}
			{isSuccess && (
				<S.UserProfile
					src={data?.avatarUrl}
					onClick={() =>
						setDropdown((prev) => ({
							isOpen: !prev.isOpen,
						}))
					}
				/>
			)}
			{dropdown.isOpen && <Dropdown onClickCloseDropdown={() => setDropdown({ isOpen: false })} />}
		</S.Container>
	);
};

export default UserProfileIcon;
