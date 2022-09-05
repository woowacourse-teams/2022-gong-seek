import { useNavigate } from 'react-router-dom';
import { useRecoilState } from 'recoil';

import * as S from '@/components/layout/TabBar/TabBar.styles';
import { URL } from '@/constants/url';
import { menuSliderState } from '@/store/menuSliderState';

const TabBar = () => {
	const navigate = useNavigate();
	const [sliderState, setSliderState] = useRecoilState(menuSliderState);

	return (
		<>
			<S.Section>
				<S.PostingLink
					onClick={() => {
						navigate(URL.CATEGORY_SELECTOR);
					}}
				/>
				<S.UserCircleLink
					onClick={() => {
						navigate(URL.MY_PAGE);
					}}
				/>
				<S.MenuLink
					onClick={() => {
						setSliderState({ isOpen: true });
					}}
				/>
			</S.Section>
		</>
	);
};

export default TabBar;
