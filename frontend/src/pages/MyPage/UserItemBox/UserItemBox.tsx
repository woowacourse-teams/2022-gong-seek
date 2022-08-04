import { ReactNode, useState } from 'react';

import * as S from '@/pages/MyPage/UserItemBox/UserItemBox.styles';

const UserItemBox = ({ children, subTitle }: { children: ReactNode; subTitle: string }) => {
	const [isOpen, setIsOpen] = useState(false);

	return (
		<S.Container>
			<S.HeaderLine>
				<S.SubTitle>{subTitle}</S.SubTitle>
				<S.DropDownButton onClick={() => setIsOpen(!isOpen)}>
					<S.DropDownImg isOpen={isOpen} />
				</S.DropDownButton>
			</S.HeaderLine>
			{isOpen && <S.ChildrenBox>{children}</S.ChildrenBox>}
		</S.Container>
	);
};

export default UserItemBox;
