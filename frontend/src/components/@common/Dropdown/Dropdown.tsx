import { useNavigate } from 'react-router-dom';

import * as S from '@/components/@common/Dropdown/Dropdown.styles';
import { ACCESSTOKEN_KEY } from '@/constants';
import { URL } from '@/constants/url';
import useDeleteRefreshToken from '@/hooks/login/useDeleteRefreshToken';

const Dropdown = ({ onCloseDropdown }: { onCloseDropdown: () => void }) => {
	const navigate = useNavigate();
	const { mutate: mutateDeleteRefreshToken } = useDeleteRefreshToken();

	const handleClickNavigateMypage = () => {
		navigate(URL.MY_PAGE);
		onCloseDropdown();
	};

	const handleClickNavigateLogout = () => {
		if (window.confirm('정말로 로그아웃을 하시겠습니까?')) {
			localStorage.removeItem(ACCESSTOKEN_KEY);
			mutateDeleteRefreshToken();
			window.location.href = '/';
			return;
		}
	};

	const handleFocusDropdownItem = (e: React.FocusEvent<HTMLDivElement>) => {
		(e.target as HTMLDivElement).ariaSelected = 'true';
	};

	const handleBlurDropdownItem = (e: React.FocusEvent<HTMLDivElement>) => {
		(e.target as HTMLDivElement).ariaSelected = 'false';
	};

	return (
		<S.Container
			role="tablist"
			aria-orientation="vertical"
			aria-controls="user-dropdown"
			tabIndex={0}
		>
			<S.DropdownItem
				onClick={handleClickNavigateMypage}
				role="tab"
				tabIndex={0}
				onFocus={handleFocusDropdownItem}
				onBlur={handleBlurDropdownItem}
				aria-selected="true"
			>
				마이페이지
			</S.DropdownItem>
			<S.DropdownItem
				onClick={handleClickNavigateLogout}
				role="tab"
				tabIndex={0}
				onFocus={handleFocusDropdownItem}
				onBlur={handleBlurDropdownItem}
				aria-selected="false"
			>
				로그아웃
			</S.DropdownItem>
		</S.Container>
	);
};

export default Dropdown;
