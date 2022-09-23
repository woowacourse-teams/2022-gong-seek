import styled from '@emotion/styled';

export const Container = styled.section`
	display: flex;
	flex-direction: column;
	align-items: center;
	justify-content: center;
	width: 100%;
	height: 68vh;
`;

export const LogoImg = styled.img`
	width: ${({ theme }) => theme.size.SIZE_090};
	height: ${({ theme }) => theme.size.SIZE_090};
	margin-bottom: ${({ theme }) => theme.size.SIZE_056};
`;

export const Message = styled.div`
	font-size: ${({ theme }) => theme.size.SIZE_020};
	font-weight: 400;
	line-height: ${({ theme }) => theme.size.SIZE_040};
	text-align: center;
	word-break: keep-all;
`;

export const HomeButton = styled.button`
	margin-top: ${({ theme }) => theme.size.SIZE_020};
	width: fit-content;
	padding: ${({ theme }) => theme.size.SIZE_004};
	background-color: transparent;
	border: ${({ theme }) => theme.size.SIZE_001} solid ${({ theme }) => theme.colors.PURPLE_500};
	border-radius: ${({ theme }) => theme.size.SIZE_004};

	&:hover,
	&:active {
		cursor: pointer;
		background-color: ${({ theme }) => theme.colors.PURPLE_500};
		color: ${({ theme }) => theme.colors.WHITE};
	}
`;
