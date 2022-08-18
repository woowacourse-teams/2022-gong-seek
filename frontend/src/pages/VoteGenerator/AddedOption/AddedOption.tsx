import React from 'react';
import { BsFillTrashFill } from 'react-icons/bs';

import styled from '@emotion/styled';

const AddedOption = ({ children, onClick }: { children: React.ReactNode; onClick: () => void }) => (
	<OptionBox>
		<p>{children}</p>
		<Trash onClick={onClick} />
	</OptionBox>
);

const OptionBox = styled.div`
	width: 80%;
	display: flex;
	justify-content: space-between;
	align-items: center;
	height: ${({ theme }) => theme.size.SIZE_040};

	border-radius: ${({ theme }) => theme.size.SIZE_006};

	font-size: ${({ theme }) => theme.size.SIZE_014};
	line-height: ${({ theme }) => theme.size.SIZE_040};

	box-shadow: 1px 1px 4px ${({ theme }) => theme.boxShadows.primary};
	padding: 0 ${({ theme }) => theme.size.SIZE_012};
`;

const Trash = styled(BsFillTrashFill)`
	font-size: ${({ theme }) => theme.size.SIZE_016};

	cursor: pointer;

	:hover {
		color: ${({ theme }) => theme.colors.RED_500};
	}
`;

export default AddedOption;
