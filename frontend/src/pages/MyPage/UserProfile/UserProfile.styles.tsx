import { FaPencilAlt, FaCheckSquare } from 'react-icons/fa';

import Input from '@/components/common/Input/Input';
import styled from '@emotion/styled';

export const Container = styled.div`
	display: flex;

	width: 100%;

	flex-direction: column;
	justify-content: center;
	align-items: center;

	gap: ${({ theme }) => theme.size.SIZE_020};
`;

export const UserProfile = styled.img`
	width: ${({ theme }) => theme.size.SIZE_050};
	height: ${({ theme }) => theme.size.SIZE_050};

	border-radius: 50%;

	object-fit: cover;
	object-position: center;
`;

export const UserName = styled.div`
	text-align: center;
	font-size: ${({ theme }) => theme.size.SIZE_014};
`;

export const UserNameBox = styled.div`
	display: flex;
	gap: ${({ theme }) => theme.size.SIZE_016};
	cursor: pointer;

	align-items: center;
`;

export const EditIcon = styled(FaPencilAlt)`
	:hover,
	:active {
		color: ${({ theme }) => theme.colors.PURPLE_400};
	}
`;

export const ConfirmIcon = styled(FaCheckSquare)`
	:hover,
	:active {
		color: ${({ theme }) => theme.colors.PURPLE_400};
	}
`;

export const EditUserNameInput = styled(Input)`
	height: ${({ theme }) => theme.size.SIZE_016};
`;
