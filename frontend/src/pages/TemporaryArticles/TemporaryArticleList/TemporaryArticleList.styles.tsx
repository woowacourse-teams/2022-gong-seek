import { AiOutlineDelete } from 'react-icons/ai';

import styled from '@emotion/styled';

export const Container = styled.section``;

export const DeleteButton = styled(AiOutlineDelete)`
	font-size: ${({ theme }) => theme.size.SIZE_020};
	margin-left: ${({ theme }) => theme.size.SIZE_020};

	&:hover,
	&:active {
		color: ${({ theme }) => theme.colors.PURPLE_500};
		cursor: pointer;
	}
`;

export const ArticleListBox = styled.div`
	display: flex;
	flex-direction: column;
	gap: ${({ theme }) => theme.size.SIZE_020};

	width: 100%;
`;

export const ArticleItemBox = styled.div`
	display: flex;
	align-items: center;

	width: 100%;
	justify-content: space-between;
`;

export const EmptyArticleMessage = styled.div``;
