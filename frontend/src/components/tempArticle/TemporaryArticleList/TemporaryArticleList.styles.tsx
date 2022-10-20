import { AiOutlineDelete } from 'react-icons/ai';

import { css } from '@emotion/react';
import styled from '@emotion/styled';

export const Container = styled.section`
	display: flex;
	justify-content: center;
	align-items: center;

	width: 100%;
`;

export const DeleteButton = styled(AiOutlineDelete)`
	${({ theme }) => css`
		font-size: ${theme.size.SIZE_024};
		margin-left: ${theme.size.SIZE_020};

		&:hover,
		&:active {
			color: ${theme.colors.PURPLE_500};
			cursor: pointer;
		}
	`}
`;

export const ArticleListBox = styled.div`
	width: 100%;
	display: flex;
	flex-direction: column;
	justify-content: center;
	align-items: center;

	gap: ${({ theme }) => theme.size.SIZE_020};
`;

export const ArticleItemBox = styled.div`
	display: flex;
	align-items: center;
	justify-content: center;

	width: 80%;
`;
