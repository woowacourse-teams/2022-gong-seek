import { useRecoilState } from 'recoil';

import Dropdown from '@/components/common/Dropdown/Dropdown';
import Loading from '@/components/common/Loading/Loading';
import * as S from '@/components/common/UserProfileIcon/UserProfileIcon.styles';
import useGetUserInfo from '@/pages/MyPage/hooks/useGetUserInfo';
import { dropdownState } from '@/store/dropdownState';

const UserProfileIcon = () => {
	const { data, isSuccess, isLoading } = useGetUserInfo();
	const [dropdown, setDropdown] = useRecoilState(dropdownState);
	if (isLoading) {
		return <Loading />;
	}

	return (
		<S.Container>
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
