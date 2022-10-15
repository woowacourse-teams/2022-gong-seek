import { AiOutlineDelete } from 'react-icons/ai';

import { css } from '@emotion/react';
import styled from '@emotion/styled';

export const Container = styled.section``;

export const DeleteButton = styled(AiOutlineDelete)`
	${({ theme }) => css`
		font-size: ${theme.size.SIZE_020};
		margin-left: ${theme.size.SIZE_020};

		&:hover,
		&:active {
			color: ${theme.colors.PURPLE_500};
			cursor: pointer;
		}
	`}
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
