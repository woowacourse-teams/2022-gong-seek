import { AiOutlineEye, AiOutlineMessage, AiFillHeart } from 'react-icons/ai';

import styled from '@emotion/styled';

export const CategoryBox = styled.div<{ categoryType: 'question' | 'discussion' }>`
	background-color: white;
	border-radius: ${({ theme }) => theme.size.SIZE_006};
	color: ${({ categoryType, theme }) =>
		categoryType === 'question' ? theme.colors.RED_500 : theme.colors.BLUE_500};
	padding: 0.5rem;
	margin-left: auto;
`;

export const CommentIcon = styled(AiOutlineMessage)`
	font-size: ${({ theme }) => theme.size.SIZE_016};

	color: ${({ theme }) => theme.colors.BLACK_600};
`;

export const ViewIcon = styled(AiOutlineEye)`
	font-size: ${({ theme }) => theme.size.SIZE_016};

	color: ${({ theme }) => theme.colors.BLACK_600};
`;

export const HeartIcon = styled(AiFillHeart)`
	font-size: ${({ theme }) => theme.size.SIZE_016};

	color: ${({ theme }) => theme.colors.RED_600};
`;

export const IconContainer = styled.div`
	display: flex;
	justify-content: center;
	align-items: center;
	gap: ${({ theme }) => theme.size.SIZE_006};
	font-size: ${({ theme }) => theme.size.SIZE_010};
	font-weight: 400;
`;
