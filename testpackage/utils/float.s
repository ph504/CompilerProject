 
.data
    nl:     .asciiz "\n"
    PI:     .float 10000000000.0
    zero:   .double 455555555.0
    buffer:   .space 20


.text

main:
    li      $v0, 2
    lwc1    $f12, PI
    syscall
    
#    li $v0, 4
#    la $a0, nl
#    syscall
    

#    li      $v0, 3
#    ldc1    $f12, zero
#    syscall
    
#    li $v0, 4
#    la $a0, nl
#    syscall

#    li $v0, 8       # take in input
#    la $a0, buffer  # load byte space into address
#    li $a1, 20      # allot the byte space for string
#    move $t0, $a0   # save string to t0
#    syscall

#    la $a0, buffer  # Load and print string asking for string
#    li $v0, 4
#    #syscall

exit:
    li      $v0, 10
    syscall
