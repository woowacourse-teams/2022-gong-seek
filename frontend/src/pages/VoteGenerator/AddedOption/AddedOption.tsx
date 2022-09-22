import React from 'react';
import { AiFillDelete } from 'react-icons/ai';

import styled from '@emotion/styled';

const AddedOption = ({ children, onClick }: { children: React.ReactNode; onClick: () => void }) => (
	<OptionBox>
		<Text>{children}</Text>
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

const Text = styled.div`
	width: 90%;
	overflow-x: auto;
	white-space: nowrap;
`;

const Trash = styled(AiFillDelete)`
	font-size: ${({ theme }) => theme.size.SIZE_016};

	cursor: pointer;

	:hover {
		color: ${({ theme }) => theme.colors.RED_500};
	}
	@media (min-width: ${({ theme }) => theme.breakpoints.DESKTOP}) {
		font-size: ${({ theme }) => theme.size.SIZE_020};
	}
`;

export default AddedOption;
