import { PropsWithStrictChildren } from 'gongseek-types';
import { useState } from 'react';

import * as S from '@/pages/MyPage/UserItemBox/UserItemBox.styles';

const UserItemBox = ({ children, subTitle }: PropsWithStrictChildren<{ subTitle: string }>) => {
	const [isContainerOpen, setIsContainerOpen] = useState(true);

	return (
		<S.Container>
			<S.HeaderLine>
				<S.SubTitle>{subTitle}</S.SubTitle>
				<S.DropDownButton onClick={() => setIsContainerOpen(!isContainerOpen)}>
					<S.DropDownImg isopen={isContainerOpen.toString()} />
				</S.DropDownButton>
			</S.HeaderLine>
			{isContainerOpen && <S.ChildrenBox>{children}</S.ChildrenBox>}
		</S.Container>
	);
};

export default UserItemBox;
